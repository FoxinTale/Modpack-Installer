package ZipFileUtility.Headers;

import ZipFileUtility.IO.Output.CountingOutputStream;
import ZipFileUtility.IO.Output.OutputStreamWithSplitZipSupport;
import ZipFileUtility.IO.Output.SplitOutputStream;
import ZipFileUtility.Model.*;
import ZipFileUtility.Util.InternalZipConstants;
import ZipFileUtility.Util.RawIO;
import ZipFileUtility.Util.Zip4jUtil;
import ZipFileUtility.ZipException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

public class HeaderWriter {

    private static final short ZIP64_EXTRA_DATA_RECORD_SIZE_LFH = 16;
    private static final short ZIP64_EXTRA_DATA_RECORD_SIZE_FH = 28;

    private final RawIO rawIO = new RawIO();
    private final byte[] longBuff = new byte[8];
    private final byte[] intBuff = new byte[4];

    public void writeLocalFileHeader(ZipModel zipModel, LocalFileHeader localFileHeader, OutputStream outputStream,
                                     Charset charset) throws IOException {

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) localFileHeader.getSignature().getValue());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, localFileHeader.getVersionNeededToExtract());
            byteArrayOutputStream.write(localFileHeader.getGeneralPurposeFlag());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, localFileHeader.getCompressionMethod().getCode());

            rawIO.writeLongLittleEndian(longBuff, 0, localFileHeader.getLastModifiedTime());
            byteArrayOutputStream.write(longBuff, 0, 4);

            rawIO.writeLongLittleEndian(longBuff, 0, localFileHeader.getCrc());
            byteArrayOutputStream.write(longBuff, 0, 4);

            boolean writeZip64Header = localFileHeader.getCompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT
                    || localFileHeader.getUncompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT;

            if (writeZip64Header) {
                rawIO.writeLongLittleEndian(longBuff, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
                byteArrayOutputStream.write(longBuff, 0, 4);
                byteArrayOutputStream.write(longBuff, 0, 4);

                zipModel.setZip64Format(true);
                localFileHeader.setWriteCompressedSizeInZip64ExtraRecord(true);
            } else {
                rawIO.writeLongLittleEndian(longBuff, 0, localFileHeader.getCompressedSize());
                byteArrayOutputStream.write(longBuff, 0, 4);

                rawIO.writeLongLittleEndian(longBuff, 0, localFileHeader.getUncompressedSize());
                byteArrayOutputStream.write(longBuff, 0, 4);

                localFileHeader.setWriteCompressedSizeInZip64ExtraRecord(false);
            }

            byte[] fileNameBytes = new byte[0];
            if (Zip4jUtil.isStringNotNullAndNotEmpty(localFileHeader.getFileName())) {
                fileNameBytes = localFileHeader.getFileName().getBytes(charset);
            }
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileNameBytes.length);

            int extraFieldLength = 0;
            if (writeZip64Header) {
                extraFieldLength += ZIP64_EXTRA_DATA_RECORD_SIZE_LFH + 4; // 4 for signature + size of record
            }
            rawIO.writeShortLittleEndian(byteArrayOutputStream, extraFieldLength);

            if (fileNameBytes.length > 0) {
                byteArrayOutputStream.write(fileNameBytes);
            }

            if (writeZip64Header) {
                rawIO.writeShortLittleEndian(byteArrayOutputStream,
                        (int) HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue());
                rawIO.writeShortLittleEndian(byteArrayOutputStream, ZIP64_EXTRA_DATA_RECORD_SIZE_LFH);
                rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getUncompressedSize());
                rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getCompressedSize());
            }


            outputStream.write(byteArrayOutputStream.toByteArray());
        }
    }

    public void finalizeZipFile(ZipModel zipModel, OutputStream outputStream, Charset charset) throws IOException {
        if (zipModel == null || outputStream == null) {
            throw new ZipException("input parameters is null, cannot finalize zip file");
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            processHeaderData(zipModel, outputStream);
            long offsetCentralDir = getOffsetOfCentralDirectory(zipModel);
            writeCentralDirectory(zipModel, byteArrayOutputStream, rawIO, charset);
            int sizeOfCentralDir = byteArrayOutputStream.size();

            if (zipModel.isZip64Format() || offsetCentralDir >= InternalZipConstants.ZIP_64_SIZE_LIMIT
                    || zipModel.getCentralDirectory().getFileHeaders().size() >= InternalZipConstants.ZIP_64_NUMBER_OF_ENTRIES_LIMIT) {

                if (zipModel.getZip64EndOfCentralDirectoryRecord() == null) {
                    zipModel.setZip64EndOfCentralDirectoryRecord(new Zip64EndOfCentralDirectoryRecord());
                }
                if (zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
                    zipModel.setZip64EndOfCentralDirectoryLocator(new Zip64EndOfCentralDirectoryLocator());
                }

                zipModel.getZip64EndOfCentralDirectoryLocator().setOffsetZip64EndOfCentralDirectoryRecord(offsetCentralDir
                        + sizeOfCentralDir);

                if (isSplitZipFile(outputStream)) {
                    int currentSplitFileCounter = getCurrentSplitFileCounter(outputStream);
                    zipModel.getZip64EndOfCentralDirectoryLocator().setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(
                            currentSplitFileCounter);
                    zipModel.getZip64EndOfCentralDirectoryLocator().setTotalNumberOfDiscs(currentSplitFileCounter + 1);
                } else {
                    zipModel.getZip64EndOfCentralDirectoryLocator().setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(0);
                    zipModel.getZip64EndOfCentralDirectoryLocator().setTotalNumberOfDiscs(1);
                }

                Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = buildZip64EndOfCentralDirectoryRecord(zipModel,
                        sizeOfCentralDir, offsetCentralDir);
                zipModel.setZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryRecord);
                writeZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryRecord, byteArrayOutputStream, rawIO);
                writeZip64EndOfCentralDirectoryLocator(zipModel.getZip64EndOfCentralDirectoryLocator(), byteArrayOutputStream, rawIO);
            }

            writeEndOfCentralDirectoryRecord(zipModel, sizeOfCentralDir, offsetCentralDir, byteArrayOutputStream, rawIO, charset);
            writeZipHeaderBytes(zipModel, outputStream, byteArrayOutputStream.toByteArray(), charset);
        }
    }

    public void finalizeZipFileWithoutValidations(ZipModel zipModel, OutputStream outputStream, Charset charset) throws IOException {

        if (zipModel == null || outputStream == null) {
            throw new ZipException("input parameters is null, cannot finalize zip file without validations");
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            long offsetCentralDir = zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory();
            writeCentralDirectory(zipModel, byteArrayOutputStream, rawIO, charset);
            int sizeOfCentralDir = byteArrayOutputStream.size();

            if (zipModel.isZip64Format() || offsetCentralDir >= InternalZipConstants.ZIP_64_SIZE_LIMIT
                    || zipModel.getCentralDirectory().getFileHeaders().size() >= InternalZipConstants.ZIP_64_NUMBER_OF_ENTRIES_LIMIT) {

                if (zipModel.getZip64EndOfCentralDirectoryRecord() == null) {
                    zipModel.setZip64EndOfCentralDirectoryRecord(new Zip64EndOfCentralDirectoryRecord());
                }
                if (zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
                    zipModel.setZip64EndOfCentralDirectoryLocator(new Zip64EndOfCentralDirectoryLocator());
                }

                zipModel.getZip64EndOfCentralDirectoryLocator().setOffsetZip64EndOfCentralDirectoryRecord(offsetCentralDir
                        + sizeOfCentralDir);

                Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = buildZip64EndOfCentralDirectoryRecord(zipModel,
                        sizeOfCentralDir, offsetCentralDir);
                zipModel.setZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryRecord);
                writeZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryRecord, byteArrayOutputStream, rawIO);
                writeZip64EndOfCentralDirectoryLocator(zipModel.getZip64EndOfCentralDirectoryLocator(), byteArrayOutputStream, rawIO);
            }

            writeEndOfCentralDirectoryRecord(zipModel, sizeOfCentralDir, offsetCentralDir, byteArrayOutputStream, rawIO, charset);
            writeZipHeaderBytes(zipModel, outputStream, byteArrayOutputStream.toByteArray(), charset);
        }
    }

    private boolean isSplitZipFile(OutputStream outputStream) {
        if (outputStream instanceof SplitOutputStream) {
            return ((SplitOutputStream) outputStream).isSplitZipFile();
        } else if (outputStream instanceof CountingOutputStream) {
            return ((CountingOutputStream) outputStream).isSplitZipFile();
        }

        return false;
    }

    private int getCurrentSplitFileCounter(OutputStream outputStream) {
        if (outputStream instanceof SplitOutputStream) {
            return ((SplitOutputStream) outputStream).getCurrentSplitFileCounter();
        }
        return ((CountingOutputStream) outputStream).getCurrentSplitFileCounter();
    }

    private void writeZipHeaderBytes(ZipModel zipModel, OutputStream outputStream, byte[] buff, Charset charset) throws IOException {
        if (buff == null) {
            throw new ZipException("invalid buff to write as zip headers");
        }

        if (outputStream instanceof CountingOutputStream) {
            if (((CountingOutputStream) outputStream).checkBuffSizeAndStartNextSplitFile(buff.length)) {
                finalizeZipFile(zipModel, outputStream, charset);
                return;
            }
        }

        outputStream.write(buff);
    }

    private void processHeaderData(ZipModel zipModel, OutputStream outputStream) throws IOException {
        int currentSplitFileCounter = 0;
        if (outputStream instanceof OutputStreamWithSplitZipSupport) {
            zipModel.getEndOfCentralDirectoryRecord().setOffsetOfStartOfCentralDirectory(
                    ((OutputStreamWithSplitZipSupport) outputStream).getFilePointer());
            currentSplitFileCounter = ((OutputStreamWithSplitZipSupport) outputStream).getCurrentSplitFileCounter();
        }

        if (zipModel.isZip64Format()) {
            if (zipModel.getZip64EndOfCentralDirectoryRecord() == null) {
                zipModel.setZip64EndOfCentralDirectoryRecord(new Zip64EndOfCentralDirectoryRecord());
            }
            if (zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
                zipModel.setZip64EndOfCentralDirectoryLocator(new Zip64EndOfCentralDirectoryLocator());
            }

            zipModel.getZip64EndOfCentralDirectoryRecord().setOffsetStartCentralDirectoryWRTStartDiskNumber(
                    zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory());
            zipModel.getZip64EndOfCentralDirectoryLocator().setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(
                    currentSplitFileCounter);
            zipModel.getZip64EndOfCentralDirectoryLocator().setTotalNumberOfDiscs(currentSplitFileCounter + 1);
        }
        zipModel.getEndOfCentralDirectoryRecord().setNumberOfThisDisk(currentSplitFileCounter);
        zipModel.getEndOfCentralDirectoryRecord().setNumberOfThisDiskStartOfCentralDir(currentSplitFileCounter);
    }

    private void writeCentralDirectory(ZipModel zipModel, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO, Charset charset)
            throws ZipException {

        if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null
                || zipModel.getCentralDirectory().getFileHeaders().size() <= 0) {
            return;
        }

        for (FileHeader fileHeader : zipModel.getCentralDirectory().getFileHeaders()) {
            writeFileHeader(zipModel, fileHeader, byteArrayOutputStream, rawIO, charset);
        }
    }

    private void writeFileHeader(ZipModel zipModel, FileHeader fileHeader, ByteArrayOutputStream byteArrayOutputStream,
                                 RawIO rawIO, Charset charset) throws ZipException {
        if (fileHeader == null) {
            throw new ZipException("input parameters is null, cannot write local file header");
        }

        try {
            final byte[] emptyShortByte = {0, 0};
            boolean writeZip64ExtendedInfo = isZip64Entry(fileHeader);

            rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) fileHeader.getSignature().getValue());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getVersionMadeBy());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getVersionNeededToExtract());
            byteArrayOutputStream.write(fileHeader.getGeneralPurposeFlag());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getCompressionMethod().getCode());

            rawIO.writeLongLittleEndian(longBuff, 0, fileHeader.getLastModifiedTime());
            byteArrayOutputStream.write(longBuff, 0, 4);

            rawIO.writeLongLittleEndian(longBuff, 0, fileHeader.getCrc());
            byteArrayOutputStream.write(longBuff, 0, 4);

            if (writeZip64ExtendedInfo) {
                rawIO.writeLongLittleEndian(longBuff, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
                byteArrayOutputStream.write(longBuff, 0, 4);
                byteArrayOutputStream.write(longBuff, 0, 4);
                zipModel.setZip64Format(true);
            } else {
                rawIO.writeLongLittleEndian(longBuff, 0, fileHeader.getCompressedSize());
                byteArrayOutputStream.write(longBuff, 0, 4);
                rawIO.writeLongLittleEndian(longBuff, 0, fileHeader.getUncompressedSize());
                byteArrayOutputStream.write(longBuff, 0, 4);
            }

            byte[] fileNameBytes = new byte[0];
            if (Zip4jUtil.isStringNotNullAndNotEmpty(fileHeader.getFileName())) {
                fileNameBytes = fileHeader.getFileName().getBytes(charset);
            }
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileNameBytes.length);

            byte[] offsetLocalHeaderBytes = new byte[4];
            if (writeZip64ExtendedInfo) {
                rawIO.writeLongLittleEndian(longBuff, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
                System.arraycopy(longBuff, 0, offsetLocalHeaderBytes, 0, 4);
            } else {
                rawIO.writeLongLittleEndian(longBuff, 0, fileHeader.getOffsetLocalHeader());
                System.arraycopy(longBuff, 0, offsetLocalHeaderBytes, 0, 4);
            }

            int extraFieldLength = calculateExtraDataRecordsSize(fileHeader, writeZip64ExtendedInfo);
            rawIO.writeShortLittleEndian(byteArrayOutputStream, extraFieldLength);

            String fileComment = fileHeader.getFileComment();
            byte[] fileCommentBytes = new byte[0];
            if (Zip4jUtil.isStringNotNullAndNotEmpty(fileComment)) {
                fileCommentBytes = fileComment.getBytes(charset);
            }
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileCommentBytes.length);

            if (writeZip64ExtendedInfo) {
                rawIO.writeIntLittleEndian(intBuff, 0, InternalZipConstants.ZIP_64_NUMBER_OF_ENTRIES_LIMIT);
                byteArrayOutputStream.write(intBuff, 0, 2);
            } else {
                rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getDiskNumberStart());
            }

            byteArrayOutputStream.write(emptyShortByte);

            //External file attributes
            byteArrayOutputStream.write(fileHeader.getExternalFileAttributes());

            //offset local header - this data is computed above
            byteArrayOutputStream.write(offsetLocalHeaderBytes);

            if (fileNameBytes.length > 0) {
                byteArrayOutputStream.write(fileNameBytes);
            }

            if (writeZip64ExtendedInfo) {
                zipModel.setZip64Format(true);

                //Zip64 header
                rawIO.writeShortLittleEndian(byteArrayOutputStream,
                        (int) HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue());

                //size of data
                rawIO.writeShortLittleEndian(byteArrayOutputStream, ZIP64_EXTRA_DATA_RECORD_SIZE_FH);
                rawIO.writeLongLittleEndian(byteArrayOutputStream, fileHeader.getUncompressedSize());
                rawIO.writeLongLittleEndian(byteArrayOutputStream, fileHeader.getCompressedSize());
                rawIO.writeLongLittleEndian(byteArrayOutputStream, fileHeader.getOffsetLocalHeader());
                rawIO.writeIntLittleEndian(byteArrayOutputStream, fileHeader.getDiskNumberStart());
            }
            writeRemainingExtraDataRecordsIfPresent(fileHeader, byteArrayOutputStream);

            if (fileCommentBytes.length > 0) {
                byteArrayOutputStream.write(fileCommentBytes);
            }
        } catch (Exception e) {
            throw new ZipException(e);
        }
    }

    private int calculateExtraDataRecordsSize(FileHeader fileHeader, boolean writeZip64ExtendedInfo) {
        int extraFieldLength = 0;
        if (writeZip64ExtendedInfo) {
            extraFieldLength += ZIP64_EXTRA_DATA_RECORD_SIZE_FH + 4; // 4 for signature + size of record
        }
        if (fileHeader.getExtraDataRecords() != null) {
            for (ExtraDataRecord extraDataRecord : fileHeader.getExtraDataRecords()) {
                if (extraDataRecord.getHeader() == HeaderSignature.AES_EXTRA_DATA_RECORD.getValue()
                        || extraDataRecord.getHeader() == HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue()) {
                    continue;
                }
                extraFieldLength += 4 + extraDataRecord.getSizeOfData(); // 4  = 2 for header + 2 for size of data
            }
        }
        return extraFieldLength;
    }

    private void writeRemainingExtraDataRecordsIfPresent(FileHeader fileHeader, OutputStream outputStream) throws IOException {
        if (fileHeader.getExtraDataRecords() == null || fileHeader.getExtraDataRecords().size() == 0) {
            return;
        }

        for (ExtraDataRecord extraDataRecord : fileHeader.getExtraDataRecords()) {
            if (extraDataRecord.getHeader() == HeaderSignature.AES_EXTRA_DATA_RECORD.getValue()
                    || extraDataRecord.getHeader() == HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue()) {
                continue;
            }

            rawIO.writeShortLittleEndian(outputStream, (int) extraDataRecord.getHeader());
            rawIO.writeShortLittleEndian(outputStream, extraDataRecord.getSizeOfData());

            if (extraDataRecord.getSizeOfData() > 0 && extraDataRecord.getData() != null) {
                outputStream.write(extraDataRecord.getData());
            }
        }
    }

    private void writeZip64EndOfCentralDirectoryRecord(Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord,
                                                       ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO) throws IOException {
        rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) zip64EndOfCentralDirectoryRecord.getSignature().getValue());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getSizeOfZip64EndCentralDirectoryRecord());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getVersionMadeBy());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getVersionNeededToExtract());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getNumberOfThisDisk());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getNumberOfThisDiskStartOfCentralDirectory());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectoryOnThisDisk());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectory());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getSizeOfCentralDirectory());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getOffsetStartCentralDirectoryWRTStartDiskNumber());
    }

    private void writeZip64EndOfCentralDirectoryLocator(Zip64EndOfCentralDirectoryLocator zip64EndOfCentralDirectoryLocator,
                                                        ByteArrayOutputStream byteArrayOutputStream,
                                                        RawIO rawIO) throws IOException {
        rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) HeaderSignature.ZIP64_END_CENTRAL_DIRECTORY_LOCATOR.getValue());
        rawIO.writeIntLittleEndian(byteArrayOutputStream,
                zip64EndOfCentralDirectoryLocator.getNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord());
        rawIO.writeLongLittleEndian(byteArrayOutputStream,
                zip64EndOfCentralDirectoryLocator.getOffsetZip64EndOfCentralDirectoryRecord());
        rawIO.writeIntLittleEndian(byteArrayOutputStream,
                zip64EndOfCentralDirectoryLocator.getTotalNumberOfDiscs());

    }

    private void writeEndOfCentralDirectoryRecord(ZipModel zipModel, int sizeOfCentralDir, long offsetCentralDir,
                                                  ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO, Charset charset)
            throws IOException {

        byte[] longByte = new byte[8];
        rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) HeaderSignature.END_OF_CENTRAL_DIRECTORY.getValue());
        rawIO.writeShortLittleEndian(byteArrayOutputStream,
                zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        rawIO.writeShortLittleEndian(byteArrayOutputStream,
                zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDiskStartOfCentralDir());

        long numEntries = zipModel.getCentralDirectory().getFileHeaders().size();
        long numEntriesOnThisDisk = numEntries;
        if (zipModel.isSplitArchive()) {
            numEntriesOnThisDisk = countNumberOfFileHeaderEntriesOnDisk(zipModel.getCentralDirectory().getFileHeaders(),
                    zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        }

        if (numEntriesOnThisDisk > InternalZipConstants.ZIP_64_NUMBER_OF_ENTRIES_LIMIT) {
            numEntriesOnThisDisk = InternalZipConstants.ZIP_64_NUMBER_OF_ENTRIES_LIMIT;
        }
        rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) numEntriesOnThisDisk);

        if (numEntries > InternalZipConstants.ZIP_64_NUMBER_OF_ENTRIES_LIMIT) {
            numEntries = InternalZipConstants.ZIP_64_NUMBER_OF_ENTRIES_LIMIT;
        }
        rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) numEntries);

        rawIO.writeIntLittleEndian(byteArrayOutputStream, sizeOfCentralDir);
        if (offsetCentralDir > InternalZipConstants.ZIP_64_SIZE_LIMIT) {
            rawIO.writeLongLittleEndian(longByte, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
            byteArrayOutputStream.write(longByte, 0, 4);
        } else {
            rawIO.writeLongLittleEndian(longByte, 0, offsetCentralDir);
            byteArrayOutputStream.write(longByte, 0, 4);
        }

        String comment = zipModel.getEndOfCentralDirectoryRecord().getComment();
        if (Zip4jUtil.isStringNotNullAndNotEmpty(comment)) {
            byte[] commentBytes = comment.getBytes(charset);
            rawIO.writeShortLittleEndian(byteArrayOutputStream, commentBytes.length);
            byteArrayOutputStream.write(commentBytes);
        } else {
            rawIO.writeShortLittleEndian(byteArrayOutputStream, 0);
        }
    }

    private long countNumberOfFileHeaderEntriesOnDisk(List<FileHeader> fileHeaders, int numOfDisk) throws ZipException {
        if (fileHeaders == null) {
            throw new ZipException("file headers are null, cannot calculate number of entries on this disk");
        }

        int noEntries = 0;
        for (FileHeader fileHeader : fileHeaders) {
            if (fileHeader.getDiskNumberStart() == numOfDisk) {
                noEntries++;
            }
        }
        return noEntries;
    }

    private boolean isZip64Entry(FileHeader fileHeader) {
        return fileHeader.getCompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT
                || fileHeader.getUncompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT
                || fileHeader.getOffsetLocalHeader() >= InternalZipConstants.ZIP_64_SIZE_LIMIT
                || fileHeader.getDiskNumberStart() >= InternalZipConstants.ZIP_64_NUMBER_OF_ENTRIES_LIMIT;
    }

    private long getOffsetOfCentralDirectory(ZipModel zipModel) {
        if (zipModel.isZip64Format()
                && zipModel.getZip64EndOfCentralDirectoryRecord() != null
                && zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber() != -1) {
            return zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber();
        }

        return zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory();
    }

    private Zip64EndOfCentralDirectoryRecord buildZip64EndOfCentralDirectoryRecord(ZipModel zipModel, int sizeOfCentralDir,
                                                                                   long offsetCentralDir) throws ZipException {

        Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = new Zip64EndOfCentralDirectoryRecord();

        zip64EndOfCentralDirectoryRecord.setSignature(HeaderSignature.ZIP64_END_CENTRAL_DIRECTORY_RECORD);
        zip64EndOfCentralDirectoryRecord.setSizeOfZip64EndCentralDirectoryRecord(44);

        if (zipModel.getCentralDirectory() != null &&
                zipModel.getCentralDirectory().getFileHeaders() != null &&
                zipModel.getCentralDirectory().getFileHeaders().size() > 0) {
            FileHeader firstFileHeader = zipModel.getCentralDirectory().getFileHeaders().get(0);
            zip64EndOfCentralDirectoryRecord.setVersionMadeBy(firstFileHeader.getVersionMadeBy());
            zip64EndOfCentralDirectoryRecord.setVersionNeededToExtract(firstFileHeader.getVersionNeededToExtract());
        }

        zip64EndOfCentralDirectoryRecord.setNumberOfThisDisk(zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDiskStartOfCentralDirectory(zipModel.getEndOfCentralDirectoryRecord()
                .getNumberOfThisDiskStartOfCentralDir());

        long numEntries = zipModel.getCentralDirectory().getFileHeaders().size();
        long numEntriesOnThisDisk = numEntries;
        if (zipModel.isSplitArchive()) {
            numEntriesOnThisDisk = countNumberOfFileHeaderEntriesOnDisk(zipModel.getCentralDirectory().getFileHeaders(),
                    zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        }

        zip64EndOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(numEntriesOnThisDisk);
        zip64EndOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectory(numEntries);
        zip64EndOfCentralDirectoryRecord.setSizeOfCentralDirectory(sizeOfCentralDir);
        zip64EndOfCentralDirectoryRecord.setOffsetStartCentralDirectoryWRTStartDiskNumber(offsetCentralDir);

        return zip64EndOfCentralDirectoryRecord;
    }
}
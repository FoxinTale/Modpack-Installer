package zip4j.io.outputstream;

import zip4j.exception.ZipException;
import zip4j.headers.FileHeaderFactory;
import zip4j.headers.HeaderSignature;
import zip4j.headers.HeaderWriter;
import zip4j.model.*;
import zip4j.model.enums.AesVersion;
import zip4j.model.enums.CompressionMethod;
import zip4j.model.enums.EncryptionMethod;
import zip4j.util.RawIO;
import zip4j.util.Zip4jUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.CRC32;

import static zip4j.util.FileUtils.isZipEntryDirectory;
import static zip4j.util.InternalZipConstants.*;

public class ZipOutputStream extends OutputStream {

  private final CountingOutputStream countingOutputStream;
  private final char[] password;
  private final ZipModel zipModel;
  private CompressedOutputStream compressedOutputStream;
  private FileHeader fileHeader;
  private LocalFileHeader localFileHeader;
  private final FileHeaderFactory fileHeaderFactory = new FileHeaderFactory();
  private final HeaderWriter headerWriter = new HeaderWriter();
  private final CRC32 crc32 = new CRC32();
  private final RawIO rawIO = new RawIO();
  private long uncompressedSizeForThisEntry = 0;
  private final Zip4jConfig zip4jConfig;
  private boolean streamClosed;
  private boolean entryClosed = true;

  public ZipOutputStream(OutputStream outputStream, char[] password, Zip4jConfig zip4jConfig,
                         ZipModel zipModel) throws IOException {
    if (zip4jConfig.getBufferSize() < MIN_BUFF_SIZE) {
      throw new IllegalArgumentException("Buffer size cannot be less than " + MIN_BUFF_SIZE + " bytes");
    }

    this.countingOutputStream = new CountingOutputStream(outputStream);
    this.password = password;
    this.zip4jConfig = zip4jConfig;
    this.zipModel = initializeZipModel(zipModel, countingOutputStream);
    this.streamClosed = false;
    writeSplitZipHeaderIfApplicable();
  }

  public void putNextEntry(ZipParameters zipParameters) throws IOException {
    verifyZipParameters(zipParameters);
    ZipParameters clonedZipParameters = cloneAndPrepareZipParameters(zipParameters);
    initializeAndWriteFileHeader(clonedZipParameters);

    //Initialisation of below compressedOutputStream should happen after writing local file header
    //because local header data should be written first and then the encryption header data
    //and below initialisation writes encryption header data
    compressedOutputStream = initializeCompressedOutputStream(clonedZipParameters);
    this.entryClosed = false;
  }

  public void write(int b) throws IOException {
    write(new byte[] {(byte)b});
  }

  public void write(byte[] b) throws IOException {
    write(b, 0, b.length);
  }

  public void write(byte[] b, int off, int len) throws IOException {
    ensureStreamOpen();
    crc32.update(b, off, len);
    compressedOutputStream.write(b, off, len);
    uncompressedSizeForThisEntry += len;
  }

  public FileHeader closeEntry() throws IOException {
    compressedOutputStream.closeEntry();

    long compressedSize = compressedOutputStream.getCompressedSize();
    fileHeader.setCompressedSize(compressedSize);
    localFileHeader.setCompressedSize(compressedSize);

    fileHeader.setUncompressedSize(uncompressedSizeForThisEntry);
    localFileHeader.setUncompressedSize(uncompressedSizeForThisEntry);

    if (writeCrc(fileHeader)) {
      fileHeader.setCrc(crc32.getValue());
      localFileHeader.setCrc(crc32.getValue());
    }

    zipModel.getLocalFileHeaders().add(localFileHeader);
    zipModel.getCentralDirectory().getFileHeaders().add(fileHeader);

    if (localFileHeader.isDataDescriptorExists()) {
      headerWriter.writeExtendedLocalHeader(localFileHeader, countingOutputStream);
    }
    reset();
    this.entryClosed = true;
    return fileHeader;
  }

  @Override
  public void close() throws IOException {
    if (!this.entryClosed) {
      closeEntry();
    }

    zipModel.getEndOfCentralDirectoryRecord().setOffsetOfStartOfCentralDirectory(countingOutputStream.getNumberOfBytesWritten());
    headerWriter.finalizeZipFile(zipModel, countingOutputStream, zip4jConfig.getCharset());
    countingOutputStream.close();
    this.streamClosed = true;
  }

  private void ensureStreamOpen() throws IOException {
    if (streamClosed) {
      throw new IOException("Stream is closed");
    }
  }

  private ZipModel initializeZipModel(ZipModel zipModel, CountingOutputStream countingOutputStream) {
    if (zipModel == null) {
      zipModel = new ZipModel();
    }

    if (countingOutputStream.isSplitZipFile()) {
      zipModel.setSplitArchive(true);
      zipModel.setSplitLength(countingOutputStream.getSplitLength());
    }

    return zipModel;
  }

  private void initializeAndWriteFileHeader(ZipParameters zipParameters) throws IOException {
    fileHeader = fileHeaderFactory.generateFileHeader(zipParameters, countingOutputStream.isSplitZipFile(),
        countingOutputStream.getCurrentSplitFileCounter(), zip4jConfig.getCharset(), rawIO);
    fileHeader.setOffsetLocalHeader(countingOutputStream.getOffsetForNextEntry());

    localFileHeader = fileHeaderFactory.generateLocalFileHeader(fileHeader);
    headerWriter.writeLocalFileHeader(zipModel, localFileHeader, countingOutputStream, zip4jConfig.getCharset());
  }

  private void reset() throws IOException {
    uncompressedSizeForThisEntry = 0;
    crc32.reset();
    compressedOutputStream.close();
  }

  private void writeSplitZipHeaderIfApplicable() throws IOException {
    if (!countingOutputStream.isSplitZipFile()) {
      return;
    }

    rawIO.writeIntLittleEndian(countingOutputStream, (int) HeaderSignature.SPLIT_ZIP.getValue());
  }

  private CompressedOutputStream initializeCompressedOutputStream(ZipParameters zipParameters) throws IOException {
    ZipEntryOutputStream zipEntryOutputStream = new ZipEntryOutputStream(countingOutputStream);
    CipherOutputStream<?> cipherOutputStream = initializeCipherOutputStream(zipEntryOutputStream, zipParameters);
    return initializeCompressedOutputStream(cipherOutputStream, zipParameters);
  }

  private CipherOutputStream<?> initializeCipherOutputStream(ZipEntryOutputStream zipEntryOutputStream,
                                                          ZipParameters zipParameters) throws IOException {
    if (!zipParameters.isEncryptFiles()) {
      return new NoCipherOutputStream(zipEntryOutputStream, zipParameters, null);
    }

    if (password == null || password.length == 0) {
      throw new ZipException("password not set");
    }

    if (zipParameters.getEncryptionMethod() == EncryptionMethod.AES) {
      return new AesCipherOutputStream(zipEntryOutputStream, zipParameters, password, zip4jConfig.isUseUtf8CharsetForPasswords());
    } else if (zipParameters.getEncryptionMethod() == EncryptionMethod.ZIP_STANDARD) {
      return new ZipStandardCipherOutputStream(zipEntryOutputStream, zipParameters, password, zip4jConfig.isUseUtf8CharsetForPasswords());
    } else if (zipParameters.getEncryptionMethod() == EncryptionMethod.ZIP_STANDARD_VARIANT_STRONG) {
      throw new ZipException(EncryptionMethod.ZIP_STANDARD_VARIANT_STRONG + " encryption method is not supported");
    } else {
      throw new ZipException("Invalid encryption method");
    }
  }

  private CompressedOutputStream initializeCompressedOutputStream(CipherOutputStream<?> cipherOutputStream,
                                                                  ZipParameters zipParameters) {
    if (zipParameters.getCompressionMethod() == CompressionMethod.DEFLATE) {
      return new DeflaterOutputStream(cipherOutputStream, zipParameters.getCompressionLevel(), zip4jConfig.getBufferSize());
    }

    return new StoreOutputStream(cipherOutputStream);
  }

  private void verifyZipParameters(ZipParameters zipParameters) {
    if (Zip4jUtil.isStringNullOrEmpty(zipParameters.getFileNameInZip())) {
      throw new IllegalArgumentException("fileNameInZip is null or empty");
    }

    if (zipParameters.getCompressionMethod() == CompressionMethod.STORE
        && zipParameters.getEntrySize() < 0
        && !isZipEntryDirectory(zipParameters.getFileNameInZip())
        && zipParameters.isWriteExtendedLocalFileHeader()) {
      throw new IllegalArgumentException("uncompressed size should be set for zip entries of compression type store");
    }
  }

  private boolean writeCrc(FileHeader fileHeader) {
    boolean isAesEncrypted = fileHeader.isEncrypted() && fileHeader.getEncryptionMethod().equals(EncryptionMethod.AES);

    if (!isAesEncrypted) {
      return true;
    }

    return fileHeader.getAesExtraDataRecord().getAesVersion().equals(AesVersion.ONE);
  }

  private ZipParameters cloneAndPrepareZipParameters(ZipParameters zipParameters) {
    ZipParameters clonedZipParameters = new ZipParameters(zipParameters);

    if (isZipEntryDirectory(zipParameters.getFileNameInZip())) {
      clonedZipParameters.setWriteExtendedLocalFileHeader(false);
      clonedZipParameters.setCompressionMethod(CompressionMethod.STORE);
      clonedZipParameters.setEncryptFiles(false);
      clonedZipParameters.setEntrySize(0);
    }

    if (zipParameters.getLastModifiedFileTime() <= 0) {
      clonedZipParameters.setLastModifiedFileTime(System.currentTimeMillis());
    }

    return clonedZipParameters;
  }
}

package ZipFileUtility.IO.Output;

import ZipFileUtility.Headers.HeaderSignature;
import ZipFileUtility.Model.RandomAccessFileMode;
import ZipFileUtility.Util.FileUtils;
import ZipFileUtility.Util.InternalZipConstants;
import ZipFileUtility.Util.RawIO;
import ZipFileUtility.ZipException;

import java.io.*;

public class SplitOutputStream extends OutputStream implements OutputStreamWithSplitZipSupport {

    private RandomAccessFile raf;
    private final long splitLength;
    private File zipFile;
    private int currSplitFileCounter;
    private long bytesWrittenForThisPart;
    private final RawIO rawIO = new RawIO();

    public SplitOutputStream(File file, long splitLength) throws FileNotFoundException, ZipException {
        if (splitLength >= 0 && splitLength < InternalZipConstants.MIN_SPLIT_LENGTH) {
            throw new ZipException("split length less than minimum allowed split length of " + InternalZipConstants.MIN_SPLIT_LENGTH + " Bytes");
        }

        this.raf = new RandomAccessFile(file, RandomAccessFileMode.WRITE.getValue());
        this.splitLength = splitLength;
        this.zipFile = file;
        this.currSplitFileCounter = 0;
        this.bytesWrittenForThisPart = 0;
    }

    public void write(int b) throws IOException {
        write(new byte[]{(byte) b});
    }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        if (len <= 0) {
            return;
        }
        if (splitLength == -1) {
            raf.write(b, off, len);
            bytesWrittenForThisPart += len;
            return;
        }
        if (bytesWrittenForThisPart >= splitLength) {
            startNextSplitFile();
            raf.write(b, off, len);
            bytesWrittenForThisPart = len;
        } else if (bytesWrittenForThisPart + len > splitLength) {
            if (isHeaderData(b)) {
                startNextSplitFile();
                raf.write(b, off, len);
                bytesWrittenForThisPart = len;
            } else {
                raf.write(b, off, (int) (splitLength - bytesWrittenForThisPart));
                startNextSplitFile();
                raf.write(b, off + (int) (splitLength - bytesWrittenForThisPart),
                        (int) (len - (splitLength - bytesWrittenForThisPart)));
                bytesWrittenForThisPart = len - (splitLength - bytesWrittenForThisPart);
            }
        } else {
            raf.write(b, off, len);
            bytesWrittenForThisPart += len;
        }
    }

    private void startNextSplitFile() throws IOException {
        String zipFileWithoutExt = FileUtils.getZipFileNameWithoutExtension(zipFile.getName());
        String zipFileName = zipFile.getAbsolutePath();
        String parentPath = (zipFile.getParent() == null) ? "" : zipFile.getParent()
                + System.getProperty("file.separator");

        String fileExtension = ".z0" + (currSplitFileCounter + 1);
        if (currSplitFileCounter >= 9) {
            fileExtension = ".z" + (currSplitFileCounter + 1);
        }

        File currSplitFile = new File(parentPath + zipFileWithoutExt + fileExtension);
        raf.close();
        if (currSplitFile.exists()) {
            throw new IOException("split file: " + currSplitFile.getName()
                    + " already exists in the current directory, cannot rename this file");
        }
        if (!zipFile.renameTo(currSplitFile)) {
            throw new IOException("cannot rename newly created split file");
        }
        zipFile = new File(zipFileName);
        raf = new RandomAccessFile(zipFile, RandomAccessFileMode.WRITE.getValue());
        currSplitFileCounter++;
    }

    private boolean isHeaderData(byte[] buff) {
        int signature = rawIO.readIntLittleEndian(buff);
        for (HeaderSignature headerSignature : HeaderSignature.values()) {
            if (headerSignature != HeaderSignature.SPLIT_ZIP &&
                    headerSignature.getValue() == signature) {
                return true;
            }
        }

        return false;
    }

    public boolean checkBufferSizeAndStartNextSplitFile(int bufferSize) throws ZipException {
        if (bufferSize < 0) {
            throw new ZipException("negative buffersize for checkBufferSizeAndStartNextSplitFile");
        }
        if (!isBufferSizeFitForCurrSplitFile(bufferSize)) {
            try {
                startNextSplitFile();
                bytesWrittenForThisPart = 0;
                return true;
            } catch (IOException e) {
                throw new ZipException(e);
            }
        }
        return false;
    }


    private boolean isBufferSizeFitForCurrSplitFile(int bufferSize) {
        if (splitLength >= InternalZipConstants.MIN_SPLIT_LENGTH) {
            return (bytesWrittenForThisPart + bufferSize <= splitLength);
        } else {
            return true;
        }
    }

    public void close() throws IOException {
        raf.close();
    }

    @Override
    public long getFilePointer() throws IOException {
        return raf.getFilePointer();
    }

    public boolean isSplitZipFile() {
        return splitLength != -1;
    }

    @Override
    public int getCurrentSplitFileCounter() {
        return currSplitFileCounter;
    }
}

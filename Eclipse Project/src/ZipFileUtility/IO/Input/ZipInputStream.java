package ZipFileUtility.IO.Input;

import ZipFileUtility.Headers.HeaderReader;
import ZipFileUtility.Model.*;
import ZipFileUtility.Util.InternalZipConstants;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.charset.Charset;
import java.util.zip.CRC32;


public class ZipInputStream extends InputStream {

    private final PushbackInputStream inputStream;
    private final HeaderReader headerReader = new HeaderReader();
    private char[] password;
    private LocalFileHeader localFileHeader;
    private CRC32 crc32 = new CRC32();
    private byte[] endOfEntryBuffer;
    private boolean canSkipExtendedLocalFileHeader = false;
    private Charset charset;


    public ZipInputStream(InputStream inputStream, char[] password, Charset charset) {
        if (charset == null) {
            charset = InternalZipConstants.CHARSET_UTF_8;
        }

        this.inputStream = new PushbackInputStream(inputStream, InternalZipConstants.BUFF_SIZE);
        this.password = password;
        this.charset = charset;
    }

    public LocalFileHeader getNextEntry(FileHeader fileHeader) throws IOException {
        if (localFileHeader != null) {
            readUntilEndOfEntry();
        }

        localFileHeader = headerReader.readLocalFileHeader(inputStream, charset);

        if (localFileHeader == null) {
            return null;
        }

        verifyLocalFileHeader(localFileHeader);
        crc32.reset();
        if (fileHeader != null) {
            localFileHeader.setCrc(fileHeader.getCrc());
            localFileHeader.setCompressedSize(fileHeader.getCompressedSize());
            localFileHeader.setUncompressedSize(fileHeader.getUncompressedSize());
            canSkipExtendedLocalFileHeader = true;
        } else {
            canSkipExtendedLocalFileHeader = false;
        }
        return localFileHeader;
    }

    @Override
    public int read() throws IOException {
        byte[] b = new byte[1];
        int readLen = read(b);

        if (readLen == -1) {
            return -1;
        }

        return b[0] & 0xff;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

/*  @Override
  public void close() throws IOException {
    if (decompressedInputStream != null) {
      decompressedInputStream.close();
    }
  }*/

    private void verifyLocalFileHeader(LocalFileHeader localFileHeader) throws IOException {
        if (!isEntryDirectory(localFileHeader.getFileName())
                && localFileHeader.getCompressionMethod() == CompressionMethod.STORE
                && localFileHeader.getUncompressedSize() < 0) {
            throw new IOException("Invalid local file header for: " + localFileHeader.getFileName()
                    + ". Uncompressed size has to be set for entry of compression type store which is not a directory");
        }
    }

    private boolean isEntryDirectory(String entryName) {
        return entryName.endsWith("/") || entryName.endsWith("\\");
    }

    private void readUntilEndOfEntry() {
        if (localFileHeader.isDirectory() || localFileHeader.getCompressedSize() == 0) {
            return;
        }
        if (endOfEntryBuffer == null) {
            endOfEntryBuffer = new byte[512];
        }
    }
}

package zip4j.io.inputstream;


import zip4j.crypto.Decrypter;
import zip4j.model.LocalFileHeader;
import zip4j.model.enums.CompressionMethod;
import zip4j.util.Zip4jUtil;

import java.io.IOException;
import java.io.InputStream;

import static zip4j.util.Zip4jUtil.readFully;

abstract class CipherInputStream<T extends Decrypter> extends InputStream {

  private final ZipEntryInputStream zipEntryInputStream;
  private final T decrypter;
  private byte[] lastReadRawDataCache;
  private final byte[] singleByteBuffer = new byte[1];
  private final LocalFileHeader localFileHeader;

  public CipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader,
                           char[] password, int bufferSize, boolean useUtf8ForPassword) throws IOException {
    this.zipEntryInputStream = zipEntryInputStream;
    this.decrypter = initializeDecrypter(localFileHeader, password, useUtf8ForPassword);
    this.localFileHeader = localFileHeader;

    if (Zip4jUtil.getCompressionMethod(localFileHeader).equals(CompressionMethod.DEFLATE)) {
      lastReadRawDataCache = new byte[bufferSize];
    }
  }

  @Override
  public int read() throws IOException {
    int readLen = read(singleByteBuffer);

    if (readLen == -1) {
      return -1;
    }

    return singleByteBuffer[0] & 0xff;
  }

  @Override
  public int read(byte[] b) throws IOException {
    return this.read(b, 0, b.length);
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    int readLen = readFully(zipEntryInputStream, b, off, len);

    if (readLen > 0) {
      cacheRawData(b, readLen);
      decrypter.decryptData(b, off, readLen);
    }

    return readLen;
  }

  @Override
  public void close() throws IOException {
    zipEntryInputStream.close();
  }

  public byte[] getLastReadRawDataCache() {
    return lastReadRawDataCache;
  }

  protected void readRaw(byte[] b) throws IOException {
    zipEntryInputStream.readRawFully(b);
  }

  private void cacheRawData(byte[] b, int len) {
    if (lastReadRawDataCache != null) {
      System.arraycopy(b, 0, lastReadRawDataCache, 0, len);
    }
  }

  public T getDecrypter() {
    return decrypter;
  }

  protected void endOfEntryReached(InputStream inputStream) throws IOException {
    // is optional but useful for AES
  }

  public LocalFileHeader getLocalFileHeader() {
    return localFileHeader;
  }

  protected abstract T initializeDecrypter(LocalFileHeader localFileHeader, char[] password,
                                           boolean useUtf8ForPassword) throws IOException;
}

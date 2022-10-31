package zip4j.io.outputstream;

import zip4j.model.enums.CompressionLevel;

import java.io.IOException;
import java.util.zip.Deflater;

class DeflaterOutputStream extends CompressedOutputStream {

  private final byte[] buff;
  protected Deflater deflater;

  public DeflaterOutputStream(CipherOutputStream<?> cipherOutputStream, CompressionLevel compressionLevel, int bufferSize) {
    super(cipherOutputStream);
    deflater = new Deflater(compressionLevel.getLevel(), true);
    buff = new byte[bufferSize];
  }

  public void write(byte[] b) throws IOException {
    write(b, 0, b.length);
  }

  public void write(int bval) throws IOException {
    byte[] b = new byte[1];
    b[0] = (byte) bval;
    write(b, 0, 1);
  }

  public void write(byte[] buf, int off, int len) throws IOException {
    deflater.setInput(buf, off, len);
    while (!deflater.needsInput()) {
      deflate();
    }
  }

  private void deflate() throws IOException {
    int len = deflater.deflate(buff, 0, buff.length);
    if (len > 0) {
      super.write(buff, 0, len);
    }
  }

  public void closeEntry() throws IOException {
    if (!deflater.finished()) {
      deflater.finish();
      while (!deflater.finished()) {
        deflate();
      }
    }
    deflater.end();
    super.closeEntry();
  }
}

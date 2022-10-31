package zip4j.io.inputstream;

import zip4j.crypto.Decrypter;
import zip4j.model.LocalFileHeader;

import java.io.IOException;

class NoCipherInputStream extends CipherInputStream<NoCipherInputStream.NoDecrypter> {

  public NoCipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader,
                             char[] password, int bufferSize) throws IOException {
    super(zipEntryInputStream, localFileHeader, password, bufferSize, true);
  }

  @Override
  protected NoDecrypter initializeDecrypter(LocalFileHeader localFileHeader, char[] password, boolean useUtf8ForPassword) {
    return new NoDecrypter();
  }

  static class NoDecrypter implements Decrypter {

    @Override
    public void decryptData(byte[] buff, int start, int len) {
    }
  }
}

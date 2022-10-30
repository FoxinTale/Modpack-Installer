package zip4j.io.outputstream;

import zip4j.crypto.Encrypter;
import zip4j.model.ZipParameters;

import java.io.IOException;
import java.io.OutputStream;

class NoCipherOutputStream extends CipherOutputStream<NoCipherOutputStream.NoEncrypter> {

  public NoCipherOutputStream(ZipEntryOutputStream zipEntryOutputStream, ZipParameters zipParameters, char[] password)
          throws IOException {
    super(zipEntryOutputStream, zipParameters, password, true);
  }

  @Override
  protected NoEncrypter initializeEncrypter(OutputStream outputStream, ZipParameters zipParameters, char[] password,
                                            boolean useUtf8ForPassword) {
    return new NoEncrypter();
  }

  static class NoEncrypter implements Encrypter {

    @Override
    public int encryptData(byte[] buff) {
      return encryptData(buff, 0, buff.length);
    }

    @Override
    public int encryptData(byte[] buff, int start, int len) {
      return len;
    }
  }
}

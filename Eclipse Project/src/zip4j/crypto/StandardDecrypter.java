package zip4j.crypto;

import zip4j.crypto.engine.ZipCryptoEngine;
import zip4j.exception.ZipException;
import zip4j.util.InternalZipConstants;

import static zip4j.util.InternalZipConstants.STD_DEC_HDR_SIZE;

public class StandardDecrypter implements Decrypter {

  private ZipCryptoEngine zipCryptoEngine;

  public StandardDecrypter(char[] password, long crc, long lastModifiedFileTime,
                           byte[] headerBytes, boolean useUtf8ForPassword) throws ZipException {
    this.zipCryptoEngine = new ZipCryptoEngine();
    init(headerBytes, password, lastModifiedFileTime, crc, useUtf8ForPassword);
  }

  public int decryptData(byte[] buff, int start, int len) throws ZipException {
    if (start < 0 || len < 0) {
      throw new ZipException("one of the input parameters were null in standard decrypt data");
    }

    for (int i = start; i < start + len; i++) {
      int val = buff[i] & 0xff;
      val = (val ^ zipCryptoEngine.decryptByte()) & 0xff;
      zipCryptoEngine.updateKeys((byte) val);
      buff[i] = (byte) val;
    }

    return len;
  }

  private void init(byte[] headerBytes, char[] password, long lastModifiedFileTime, long crc,
                    boolean useUtf8ForPassword) throws ZipException {
    if (password == null || password.length <= 0) {
      throw new ZipException("Wrong password!", ZipException.Type.WRONG_PASSWORD);
    }

    zipCryptoEngine.initKeys(password, useUtf8ForPassword);

    int result = headerBytes[0];
    for (int i = 0; i < STD_DEC_HDR_SIZE; i++) {
      if (i + 1 == InternalZipConstants.STD_DEC_HDR_SIZE) {
        byte verificationByte = (byte) (result ^ zipCryptoEngine.decryptByte());
        if (verificationByte != (byte) (crc >> 24) && verificationByte != (byte) (lastModifiedFileTime >> 8)) {
          throw new ZipException("Wrong password!", ZipException.Type.WRONG_PASSWORD);
        }
      }

      zipCryptoEngine.updateKeys((byte) (result ^ zipCryptoEngine.decryptByte()));

      if (i + 1 != STD_DEC_HDR_SIZE) {
        result = headerBytes[i + 1];
      }
    }
  }

}

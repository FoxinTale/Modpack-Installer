package zip4j.crypto.engine;

import static zip4j.util.Zip4jUtil.convertCharArrayToByteArray;

public class ZipCryptoEngine {

  private final int[] keys = new int[3];
  private static final int[] CRC_TABLE = new int[256];

  static {
    for (int i = 0; i < 256; i++) {
      int r = i;
      for (int j = 0; j < 8; j++) {
        if ((r & 1) == 1) {
          r = (r >>> 1) ^ 0xedb88320;
        } else {
          r >>>= 1;
        }
      }
      CRC_TABLE[i] = r;
    }
  }

  public void initKeys(char[] password, boolean useUtf8ForPassword) {
    keys[0] = 305419896;
    keys[1] = 591751049;
    keys[2] = 878082192;
    byte[] bytes = convertCharArrayToByteArray(password, useUtf8ForPassword);
    for (byte b : bytes) {
      updateKeys((byte) (b & 0xff));
    }
  }

  public void updateKeys(byte charAt) {
    keys[0] = crc32(keys[0], charAt);
    keys[1] += keys[0] & 0xff;
    keys[1] = keys[1] * 134775813 + 1;
    keys[2] = crc32(keys[2], (byte) (keys[1] >> 24));
  }

  private int crc32(int oldCrc, byte charAt) {
    return ((oldCrc >>> 8) ^ CRC_TABLE[(oldCrc ^ charAt) & 0xff]);
  }

  public byte decryptByte() {
    int temp = keys[2] | 2;
    return (byte) ((temp * (temp ^ 1)) >>> 8);
  }
}

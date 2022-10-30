package zip4j.crypto;

import zip4j.exception.ZipException;

public interface Decrypter {

  int decryptData(byte[] buff, int start, int len) throws ZipException, ZipException;

}

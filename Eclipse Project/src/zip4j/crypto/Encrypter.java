package zip4j.crypto;

import zip4j.exception.ZipException;

public interface Encrypter {

  int encryptData(byte[] buff) throws ZipException;

  int encryptData(byte[] buff, int start, int len) throws ZipException;

}

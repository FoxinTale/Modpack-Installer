package ZipFileUtility.Crypto;

import ZipFileUtility.ZipException;

public interface Decrypter {
    int decryptData(byte[] buff, int start, int len) throws ZipException;
}
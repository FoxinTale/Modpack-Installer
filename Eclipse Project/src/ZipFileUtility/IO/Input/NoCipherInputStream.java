package ZipFileUtility.IO.Input;


import ZipFileUtility.Crypto.Decrypter;
import ZipFileUtility.Model.LocalFileHeader;
import ZipFileUtility.ZipException;

import java.io.IOException;

class NoCipherInputStream extends CipherInputStream {

    public NoCipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader, char[] password) throws IOException, ZipException {
        super(zipEntryInputStream, localFileHeader, password);
    }

    @Override
    protected Decrypter initializeDecrypter(LocalFileHeader localFileHeader, char[] password) {
        return new NoDecrypter();
    }

    static class NoDecrypter implements Decrypter {

        @Override
        public int decryptData(byte[] buff, int start, int len) {
            return len;
        }
    }
}


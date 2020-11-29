package ZipFileUtility.Crypto;

import ZipFileUtility.Util.InternalZipConstants;
import ZipFileUtility.ZipException;

import java.security.SecureRandom;

public class StandardEncrypter implements Encrypter {

    private ZipCryptoEngine zipCryptoEngine;
    private byte[] headerBytes;

    public StandardEncrypter(char[] password, long key) throws ZipException {
        this.zipCryptoEngine = new ZipCryptoEngine();

        this.headerBytes = new byte[InternalZipConstants.STD_DEC_HDR_SIZE];
        init(password, key);
    }

    private void init(char[] password, long key) throws ZipException {
        if (password == null || password.length <= 0) {
            throw new ZipException("input password is null or empty, cannot initialize standard encrypter");
        }
        zipCryptoEngine.initKeys(password);
        headerBytes = generateRandomBytes(InternalZipConstants.STD_DEC_HDR_SIZE);
        zipCryptoEngine.initKeys(password);

        headerBytes[InternalZipConstants.STD_DEC_HDR_SIZE - 1] = (byte) ((key >>> 24));
        headerBytes[InternalZipConstants.STD_DEC_HDR_SIZE - 2] = (byte) ((key >>> 16));

        if (headerBytes.length < InternalZipConstants.STD_DEC_HDR_SIZE) {
            throw new ZipException("invalid header bytes generated, cannot perform standard encryption");
        }

        encryptData(headerBytes);
    }

    public int encryptData(byte[] buff) throws ZipException {
        if (buff == null) {
            throw new NullPointerException();
        }
        return encryptData(buff, 0, buff.length);
    }

    public int encryptData(byte[] buff, int start, int len) throws ZipException {
        if (len < 0) {
            throw new ZipException("invalid length specified to decrpyt data");
        }

        for (int i = start; i < start + len; i++) {
            buff[i] = encryptByte(buff[i]);
        }
        return len;
    }

    protected byte encryptByte(byte val) {
        byte temp_val = (byte) (val ^ zipCryptoEngine.decryptByte() & 0xff);
        zipCryptoEngine.updateKeys(val);
        return temp_val;
    }

    protected byte[] generateRandomBytes(int size) throws ZipException {
        if (size <= 0) {
            throw new ZipException("size is either 0 or less than 0, cannot generate header for standard encryptor");
        }

        byte[] buff = new byte[size];
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < buff.length; i++) {
            buff[i] = encryptByte((byte) random.nextInt(256));
        }

        return buff;
    }

    public byte[] getHeaderBytes() {
        return headerBytes;
    }

}

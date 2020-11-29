package ZipFileUtility.Crypto;

import ZipFileUtility.Util.InternalZipConstants;
import ZipFileUtility.ZipException;

public class StandardDecrypter implements Decrypter {

    private char[] password;
    private byte[] crcBytes;
    private byte[] crc = new byte[4];
    private ZipCryptoEngine zipCryptoEngine;

    public StandardDecrypter(char[] password, byte[] crcBytes , byte[] headerBytes) throws ZipException {
        this.password = password;
        this.crcBytes = crcBytes;
        this.zipCryptoEngine = new ZipCryptoEngine();
        init(headerBytes);
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

    private void init(byte[] headerBytes) throws ZipException {
        crc[3] = (byte) (crcBytes[3] & 0xFF);
        crc[2] = (byte) ((crcBytes[3] >> 8) & 0xFF);
        crc[1] = (byte) ((crcBytes[3] >> 16) & 0xFF);
        crc[0] = (byte) ((crcBytes[3] >> 24) & 0xFF);

        if (crc[2] > 0 || crc[1] > 0 || crc[0] > 0)
            throw new IllegalStateException("Invalid CRC in File Header");

        if (password == null || password.length <= 0) {
            throw new ZipException("Wrong password!", ZipException.Type.WRONG_PASSWORD);
        }

        zipCryptoEngine.initKeys(password);

        int result = headerBytes[0];
        for (int i = 0; i < InternalZipConstants.STD_DEC_HDR_SIZE; i++) {
            zipCryptoEngine.updateKeys((byte) (result ^ zipCryptoEngine.decryptByte()));
            if (i + 1 != InternalZipConstants.STD_DEC_HDR_SIZE)
                result = headerBytes[i + 1];
        }
    }

}

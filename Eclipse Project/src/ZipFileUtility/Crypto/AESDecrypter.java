package ZipFileUtility.Crypto;

import ZipFileUtility.Model.AESExtraDataRecord;
import ZipFileUtility.Model.AesKeyStrength;
import ZipFileUtility.Util.InternalZipConstants;
import ZipFileUtility.ZipException;

import java.util.Arrays;

public class AESDecrypter implements Decrypter {

    private AESExtraDataRecord aesExtraDataRecord;
    private char[] password;
    private AESEngine aesEngine;
    private MacBasedPRF mac;

    private int nonce = 1;
    private byte[] iv;
    private byte[] counterBlock;

    public AESDecrypter(AESExtraDataRecord aesExtraDataRecord, char[] password, byte[] salt, byte[] passwordVerifier) throws ZipException {
        this.aesExtraDataRecord = aesExtraDataRecord;
        this.password = password;
        iv = new byte[InternalZipConstants.AES_BLOCK_SIZE];
        counterBlock = new byte[InternalZipConstants.AES_BLOCK_SIZE];
        init(salt, passwordVerifier);
    }

    private void init(byte[] salt, byte[] passwordVerifier) throws ZipException {
        if (password == null || password.length <= 0) {
            throw new ZipException("empty or null password provided for AES decryption");
        }

        final AesKeyStrength aesKeyStrength = aesExtraDataRecord.getAesKeyStrength();
        final byte[] derivedKey = AesCipherUtil.derivePasswordBasedKey(salt, password, aesKeyStrength);
        final byte[] derivedPasswordVerifier = AesCipherUtil.derivePasswordVerifier(derivedKey, aesKeyStrength);
        if (!Arrays.equals(passwordVerifier, derivedPasswordVerifier)) {
            throw new ZipException("Wrong Password", ZipException.Type.WRONG_PASSWORD);
        }

        aesEngine = AesCipherUtil.getAESEngine(derivedKey, aesKeyStrength);
        mac = AesCipherUtil.getMacBasedPRF(derivedKey, aesKeyStrength);
    }

    @Override
    public int decryptData(byte[] buff, int start, int len) throws ZipException {

        for (int j = start; j < (start + len); j += InternalZipConstants.AES_BLOCK_SIZE) {
            int loopCount = (j + InternalZipConstants.AES_BLOCK_SIZE <= (start + len)) ?
                    InternalZipConstants.AES_BLOCK_SIZE : ((start + len) - j);

            mac.update(buff, j, loopCount);
            AesCipherUtil.prepareBuffAESIVBytes(iv, nonce);
            aesEngine.processBlock(iv, counterBlock);

            for (int k = 0; k < loopCount; k++) {
                buff[j + k] = (byte) (buff[j + k] ^ counterBlock[k]);
            }

            nonce++;
        }

        return len;
    }

    public byte[] getCalculatedAuthenticationBytes() {
        return mac.doFinal();
    }
}

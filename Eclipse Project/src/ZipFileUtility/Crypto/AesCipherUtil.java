package ZipFileUtility.Crypto;

import ZipFileUtility.Model.AesKeyStrength;
import ZipFileUtility.Util.InternalZipConstants;
import ZipFileUtility.ZipException;

public class AesCipherUtil {
    private static final int START_INDEX = 0;

    public static byte[] derivePasswordBasedKey(final byte[] salt, final char[] password, final AesKeyStrength aesKeyStrength) throws ZipException {
        final PBKDF2Parameters parameters = new PBKDF2Parameters(InternalZipConstants.AES_MAC_ALGORITHM, InternalZipConstants.AES_HASH_CHARSET, salt, InternalZipConstants.AES_HASH_ITERATIONS);
        final PBKDF2Engine engine = new PBKDF2Engine(parameters);

        final int keyLength = aesKeyStrength.getKeyLength();
        final int macLength = aesKeyStrength.getMacLength();
        final int derivedKeyLength = keyLength + macLength + InternalZipConstants.AES_PASSWORD_VERIFIER_LENGTH;
        final byte[] derivedKey = engine.deriveKey(password, derivedKeyLength);
        if (derivedKey != null && derivedKey.length == derivedKeyLength) {
            return derivedKey;
        } else {
            final String message = String.format("Derived Key invalid for Key Length [%d] MAC Length [%d]", keyLength, macLength);
            throw new ZipException(message);
        }
    }

    public static byte[] derivePasswordVerifier(final byte[] derivedKey, final AesKeyStrength aesKeyStrength) {
        byte[] derivedPasswordVerifier = new byte[InternalZipConstants.AES_PASSWORD_VERIFIER_LENGTH];
        final int keyMacLength = aesKeyStrength.getKeyLength() + aesKeyStrength.getMacLength();
        System.arraycopy(derivedKey, keyMacLength, derivedPasswordVerifier, START_INDEX, InternalZipConstants.AES_PASSWORD_VERIFIER_LENGTH);
        return derivedPasswordVerifier;
    }

    public static MacBasedPRF getMacBasedPRF(final byte[] derivedKey, final AesKeyStrength aesKeyStrength) {
        final int macLength = aesKeyStrength.getMacLength();
        final byte[] macKey = new byte[macLength];
        System.arraycopy(derivedKey, aesKeyStrength.getKeyLength(), macKey, START_INDEX, macLength);
        final MacBasedPRF macBasedPRF = new MacBasedPRF(InternalZipConstants.AES_MAC_ALGORITHM);
        macBasedPRF.init(macKey);
        return macBasedPRF;
    }

    public static AESEngine getAESEngine(final byte[] derivedKey, final AesKeyStrength aesKeyStrength) throws ZipException {
        final int keyLength = aesKeyStrength.getKeyLength();
        final byte[] aesKey = new byte[keyLength];
        System.arraycopy(derivedKey, START_INDEX, aesKey, START_INDEX, keyLength);
        return new AESEngine(aesKey);
    }

    public static void prepareBuffAESIVBytes(byte[] buff, int nonce) {
        buff[0] = (byte) nonce;
        buff[1] = (byte) (nonce >> 8);
        buff[2] = (byte) (nonce >> 16);
        buff[3] = (byte) (nonce >> 24);

        for (int i = 4; i <= 15; i++) {
            buff[i] = 0;
        }
    }

}

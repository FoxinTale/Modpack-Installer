package ZipFileUtility.IO.Input;


import ZipFileUtility.Crypto.AESDecrypter;
import ZipFileUtility.Model.AESExtraDataRecord;
import ZipFileUtility.Model.CompressionMethod;
import ZipFileUtility.Model.LocalFileHeader;
import ZipFileUtility.Util.InternalZipConstants;
import ZipFileUtility.Util.Zip4jUtil;
import ZipFileUtility.ZipException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

class AesCipherInputStream extends CipherInputStream<AESDecrypter> {

    private byte[] singleByteBuffer = new byte[1];
    private byte[] aes16ByteBlock = new byte[16];
    private int aes16ByteBlockPointer = 0;
    private int remainingAes16ByteBlockLength = 0;
    private int lengthToRead = 0;
    private int offsetWithAesBlock = 0;
    private int bytesCopiedInThisIteration = 0;
    private int lengthToCopyInThisIteration = 0;
    private int aes16ByteBlockReadLength = 0;

    public AesCipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader, char[] password)
            throws IOException {
        super(zipEntryInputStream, localFileHeader, password);
    }

    @Override
    protected AESDecrypter initializeDecrypter(LocalFileHeader localFileHeader, char[] password) throws IOException {
        return new AESDecrypter(localFileHeader.getAesExtraDataRecord(), password, getSalt(localFileHeader), getPasswordVerifier());
    }

    @Override
    public int read() throws IOException {
        int readLen = read(singleByteBuffer);

        if (readLen == -1) {
            return -1;
        }

        return singleByteBuffer[0];
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        lengthToRead = len;
        offsetWithAesBlock = off;
        bytesCopiedInThisIteration = 0;

        if (remainingAes16ByteBlockLength != 0) {
            copyBytesFromBuffer(b, offsetWithAesBlock);

            if (bytesCopiedInThisIteration == len) {
                return bytesCopiedInThisIteration;
            }
        }

        if (lengthToRead < 16) {
            aes16ByteBlockReadLength = super.read(aes16ByteBlock, 0, aes16ByteBlock.length);
            aes16ByteBlockPointer = 0;

            if (aes16ByteBlockReadLength == -1) {
                remainingAes16ByteBlockLength = 0;

                if (bytesCopiedInThisIteration > 0) {
                    return bytesCopiedInThisIteration;
                }

                return -1;
            }

            remainingAes16ByteBlockLength = aes16ByteBlockReadLength;

            copyBytesFromBuffer(b, offsetWithAesBlock);

            if (bytesCopiedInThisIteration == len) {
                return bytesCopiedInThisIteration;
            }
        }

        int readLen = super.read(b, offsetWithAesBlock, (lengthToRead - lengthToRead %16));

        if (readLen == -1) {
            if (bytesCopiedInThisIteration > 0) {
                return bytesCopiedInThisIteration;
            } else {
                return -1;
            }
        } else {
            return readLen + bytesCopiedInThisIteration;
        }
    }

    private void copyBytesFromBuffer(byte[] b, int off) {
        lengthToCopyInThisIteration = Math.min(lengthToRead, remainingAes16ByteBlockLength);
        System.arraycopy(aes16ByteBlock, aes16ByteBlockPointer, b, off, lengthToCopyInThisIteration);

        incrementAesByteBlockPointer(lengthToCopyInThisIteration);
        decrementRemainingAesBytesLength(lengthToCopyInThisIteration);

        bytesCopiedInThisIteration += lengthToCopyInThisIteration;

        lengthToRead -= lengthToCopyInThisIteration;
        offsetWithAesBlock += lengthToCopyInThisIteration;
    }

    @Override
    protected void endOfEntryReached(InputStream inputStream) throws IOException {
        verifyContent(readStoredMac(inputStream));
    }

    private void verifyContent(byte[] storedMac) throws IOException {
        if (getLocalFileHeader().isDataDescriptorExists()
                && CompressionMethod.DEFLATE.equals(Zip4jUtil.getCompressionMethod(getLocalFileHeader()))) {
            return;
        }

        byte[] calculatedMac = getDecrypter().getCalculatedAuthenticationBytes();
        byte[] first10BytesOfCalculatedMac = new byte[InternalZipConstants.AES_AUTH_LENGTH];
        System.arraycopy(calculatedMac, 0, first10BytesOfCalculatedMac, 0, InternalZipConstants.AES_AUTH_LENGTH);

        if (!Arrays.equals(storedMac, first10BytesOfCalculatedMac)) {
            throw new IOException("Reached end of data for this entry, but aes verification failed");
        }
    }

    protected byte[] readStoredMac(InputStream inputStream) throws IOException {
        byte[] storedMac = new byte[InternalZipConstants.AES_AUTH_LENGTH];
        int readLen = Zip4jUtil.readFully(inputStream, storedMac);

        if (readLen != InternalZipConstants.AES_AUTH_LENGTH) {
            throw new ZipException("Invalid AES Mac bytes. Could not read sufficient data");
        }

        return storedMac;
    }

    private byte[] getSalt(LocalFileHeader localFileHeader) throws IOException {
        if (localFileHeader.getAesExtraDataRecord() == null) {
            throw new IOException("invalid aes extra data record");
        }

        AESExtraDataRecord aesExtraDataRecord = localFileHeader.getAesExtraDataRecord();
        byte[] saltBytes = new byte[aesExtraDataRecord.getAesKeyStrength().getSaltLength()];
        readRaw(saltBytes);
        return saltBytes;
    }

    private byte[] getPasswordVerifier() throws IOException {
        byte[] pvBytes = new byte[2];
        readRaw(pvBytes);
        return pvBytes;
    }

    private void incrementAesByteBlockPointer(int incrementBy) {
        aes16ByteBlockPointer += incrementBy;

        if (aes16ByteBlockPointer >= 15) {
            aes16ByteBlockPointer = 15;
        }
    }

    private void decrementRemainingAesBytesLength(int decrementBy) {
        remainingAes16ByteBlockLength -= decrementBy;

        if (remainingAes16ByteBlockLength <= 0) {
            remainingAes16ByteBlockLength = 0;
        }
    }
}


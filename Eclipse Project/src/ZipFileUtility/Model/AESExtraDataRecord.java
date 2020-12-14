package ZipFileUtility.Model;


import ZipFileUtility.Headers.HeaderSignature;

public class AESExtraDataRecord extends ZipHeader {

    private int dataSize;
    private AesVersion aesVersion;
    private String vendorID;
    private AesKeyStrength aesKeyStrength;
    private CompressionMethod compressionMethod;

    public AESExtraDataRecord() {
        setSignature(HeaderSignature.AES_EXTRA_DATA_RECORD);
        dataSize = 7;
        aesVersion = AesVersion.TWO;
        vendorID = "AE";
        aesKeyStrength = AesKeyStrength.KEY_STRENGTH_256;
        compressionMethod = CompressionMethod.DEFLATE;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public AesVersion getAesVersion() {
        return aesVersion;
    }

    public void setAesVersion(AesVersion aesVersion) {
        this.aesVersion = aesVersion;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public AesKeyStrength getAesKeyStrength() {
        return aesKeyStrength;
    }

    public void setAesKeyStrength(AesKeyStrength aesKeyStrength) {
        this.aesKeyStrength = aesKeyStrength;
    }

    public CompressionMethod getCompressionMethod() {
        return compressionMethod;
    }

    public void setCompressionMethod(CompressionMethod compressionMethod) {
        this.compressionMethod = compressionMethod;
    }
}
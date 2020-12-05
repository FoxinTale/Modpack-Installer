package ZipFileUtility.Model;

public class DigitalSignature extends ZipHeader {
    private int sizeOfData;
    public int getSizeOfData() {
        return sizeOfData;
    }
    public void setSizeOfData(int sizeOfData) {
        this.sizeOfData = sizeOfData;
    }
    public void setSignatureData() {
    }

}

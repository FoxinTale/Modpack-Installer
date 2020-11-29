package ZipFileUtility.Model;

public class DigitalSignature  extends ZipHeader {

  private int sizeOfData;
  private String signatureData;

  public int getSizeOfData() {
    return sizeOfData;
  }

  public void setSizeOfData(int sizeOfData) {
    this.sizeOfData = sizeOfData;
  }

/*
  public String getSignatureData() {
    return signatureData;
  }
*/

  public void setSignatureData(String signatureData) {
    this.signatureData = signatureData;
  }

}

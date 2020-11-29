package ZipFileUtility.Model;

public class ArchiveExtraDataRecord extends ZipHeader {

  private int extraFieldLength;
  private String extraFieldData;

  public int getExtraFieldLength() {
    return extraFieldLength;
  }

/*
  public void setExtraFieldLength(int extraFieldLength) {
    this.extraFieldLength = extraFieldLength;
  }

  public String getExtraFieldData() {
    return extraFieldData;
  }
*/

  public void setExtraFieldData(String extraFieldData) {
    this.extraFieldData = extraFieldData;
  }

}

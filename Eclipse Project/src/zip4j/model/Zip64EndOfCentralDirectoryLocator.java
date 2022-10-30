package zip4j.model;


public class Zip64EndOfCentralDirectoryLocator extends ZipHeader {

  private int numberOfDiskStartOfZip64EndOfCentralDirectoryRecord;
  private long offsetZip64EndOfCentralDirectoryRecord;
  private int totalNumberOfDiscs;

  public int getNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord() {
    return numberOfDiskStartOfZip64EndOfCentralDirectoryRecord;
  }

  public void setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(
      int noOfDiskStartOfZip64EndOfCentralDirRec) {
    this.numberOfDiskStartOfZip64EndOfCentralDirectoryRecord = noOfDiskStartOfZip64EndOfCentralDirRec;
  }

  public long getOffsetZip64EndOfCentralDirectoryRecord() {
    return offsetZip64EndOfCentralDirectoryRecord;
  }

  public void setOffsetZip64EndOfCentralDirectoryRecord(long offsetZip64EndOfCentralDirectoryRecord) {
    this.offsetZip64EndOfCentralDirectoryRecord = offsetZip64EndOfCentralDirectoryRecord;
  }

  public int getTotalNumberOfDiscs() {
    return totalNumberOfDiscs;
  }

  public void setTotalNumberOfDiscs(int totNumberOfDiscs) {
    this.totalNumberOfDiscs = totNumberOfDiscs;
  }


}

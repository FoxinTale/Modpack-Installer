package zip4j.model;


public class Zip64EndOfCentralDirectoryRecord extends ZipHeader {

  private long sizeOfZip64EndCentralDirectoryRecord;
  private int versionMadeBy;
  private int versionNeededToExtract;
  private int numberOfThisDisk;
  private int numberOfThisDiskStartOfCentralDirectory;
  private long totalNumberOfEntriesInCentralDirectoryOnThisDisk;
  private long totalNumberOfEntriesInCentralDirectory;
  private long sizeOfCentralDirectory;
  private long offsetStartCentralDirectoryWRTStartDiskNumber = -1;
  private byte[] extensibleDataSector;

  public long getSizeOfZip64EndCentralDirectoryRecord() {
    return sizeOfZip64EndCentralDirectoryRecord;
  }

  public void setSizeOfZip64EndCentralDirectoryRecord(long sizeOfZip64EndCentralDirectoryRecord) {
    this.sizeOfZip64EndCentralDirectoryRecord = sizeOfZip64EndCentralDirectoryRecord;
  }

  public int getVersionMadeBy() {
    return versionMadeBy;
  }

  public void setVersionMadeBy(int versionMadeBy) {
    this.versionMadeBy = versionMadeBy;
  }

  public int getVersionNeededToExtract() {
    return versionNeededToExtract;
  }

  public void setVersionNeededToExtract(int versionNeededToExtract) {
    this.versionNeededToExtract = versionNeededToExtract;
  }

  public int getNumberOfThisDisk() {
    return numberOfThisDisk;
  }

  public void setNumberOfThisDisk(int numberOfThisDisk) {
    this.numberOfThisDisk = numberOfThisDisk;
  }

  public int getNumberOfThisDiskStartOfCentralDirectory() {
    return numberOfThisDiskStartOfCentralDirectory;
  }

  public void setNumberOfThisDiskStartOfCentralDirectory(int numberOfThisDiskStartOfCentralDirectory) {
    this.numberOfThisDiskStartOfCentralDirectory = numberOfThisDiskStartOfCentralDirectory;
  }

  public long getTotalNumberOfEntriesInCentralDirectoryOnThisDisk() {
    return totalNumberOfEntriesInCentralDirectoryOnThisDisk;
  }

  public void setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(
      long totalNumberOfEntriesInCentralDirectoryOnThisDisk) {
    this.totalNumberOfEntriesInCentralDirectoryOnThisDisk = totalNumberOfEntriesInCentralDirectoryOnThisDisk;
  }

  public long getTotalNumberOfEntriesInCentralDirectory() {
    return totalNumberOfEntriesInCentralDirectory;
  }

  public void setTotalNumberOfEntriesInCentralDirectory(long totalNumberOfEntriesInCentralDirectory) {
    this.totalNumberOfEntriesInCentralDirectory = totalNumberOfEntriesInCentralDirectory;
  }

  public long getSizeOfCentralDirectory() {
    return sizeOfCentralDirectory;
  }

  public void setSizeOfCentralDirectory(long sizeOfCentralDirectory) {
    this.sizeOfCentralDirectory = sizeOfCentralDirectory;
  }

  public long getOffsetStartCentralDirectoryWRTStartDiskNumber() {
    return offsetStartCentralDirectoryWRTStartDiskNumber;
  }

  public void setOffsetStartCentralDirectoryWRTStartDiskNumber(
      long offsetStartCentralDirectoryWRTStartDiskNumber) {
    this.offsetStartCentralDirectoryWRTStartDiskNumber = offsetStartCentralDirectoryWRTStartDiskNumber;
  }

  public byte[] getExtensibleDataSector() {
    return extensibleDataSector;
  }

  public void setExtensibleDataSector(byte[] extensibleDataSector) {
    this.extensibleDataSector = extensibleDataSector;
  }


}

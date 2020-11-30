package ZipFileUtility.Model;

public class Zip64ExtendedInfo extends ZipHeader {

  private int size;
  private long compressedSize;
  private long uncompressedSize;
  private long offsetLocalHeader;
  private int diskNumberStart;

  public Zip64ExtendedInfo() {
    compressedSize = -1;
    uncompressedSize = -1;
    offsetLocalHeader = -1;
    diskNumberStart = -1;
  }

  public int getSize() {
    return size;
  }
  public void setSize(int size) {
    this.size = size;
  }
  public long getCompressedSize() {
    return compressedSize;
  }
  public void setCompressedSize(long compressedSize) {
    this.compressedSize = compressedSize;
  }
  public long getUncompressedSize() {
    return uncompressedSize;
  }
  public void setUncompressedSize(long uncompressedSize) {
    this.uncompressedSize = uncompressedSize;
  }
  public long getOffsetLocalHeader() {
    return offsetLocalHeader;
  }
  public void setOffsetLocalHeader(long offsetLocalHeader) {
    this.offsetLocalHeader = offsetLocalHeader;
  }
  public int getDiskNumberStart() {
    return diskNumberStart;
  }
  public void setDiskNumberStart(int diskNumberStart) {
    this.diskNumberStart = diskNumberStart;
  }


}

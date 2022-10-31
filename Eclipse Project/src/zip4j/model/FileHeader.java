package zip4j.model;

import zip4j.headers.HeaderSignature;

import java.util.Objects;

public class FileHeader extends AbstractFileHeader {

  private int versionMadeBy;
  private int diskNumberStart;
  private byte[] externalFileAttributes;
  private long offsetLocalHeader;
  private String fileComment;

  public FileHeader() {
    setSignature(HeaderSignature.CENTRAL_DIRECTORY);
  }

  public int getVersionMadeBy() {
    return versionMadeBy;
  }

  public void setVersionMadeBy(int versionMadeBy) {
    this.versionMadeBy = versionMadeBy;
  }

  public void setFileCommentLength() {
  }

  public int getDiskNumberStart() {
    return diskNumberStart;
  }

  public void setDiskNumberStart(int diskNumberStart) {
    this.diskNumberStart = diskNumberStart;
  }

  public void setInternalFileAttributes(byte[] internalFileAttributes) {
  }

  public byte[] getExternalFileAttributes() {
    return externalFileAttributes;
  }

  public void setExternalFileAttributes(byte[] externalFileAttributes) {
    this.externalFileAttributes = externalFileAttributes;
  }

  public long getOffsetLocalHeader() {
    return offsetLocalHeader;
  }

  public void setOffsetLocalHeader(long offsetLocalHeader) {
    this.offsetLocalHeader = offsetLocalHeader;
  }

  public String getFileComment() {
    return fileComment;
  }

  public void setFileComment(String fileComment) {
    this.fileComment = fileComment;
  }

  @Override
  public String toString() {
    return getFileName();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    FileHeader that = (FileHeader) o;
    return determineOffsetOfLocalFileHeader(this) == determineOffsetOfLocalFileHeader(that);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getFileName(), determineOffsetOfLocalFileHeader(this));
  }

  private long determineOffsetOfLocalFileHeader(FileHeader fileHeader) {
    if (fileHeader.getZip64ExtendedInfo() != null) {
      return fileHeader.getZip64ExtendedInfo().getOffsetLocalHeader();
    }

    return fileHeader.getOffsetLocalHeader();
  }
}

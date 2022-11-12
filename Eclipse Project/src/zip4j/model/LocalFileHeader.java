
package zip4j.model;

import zip4j.headers.HeaderSignature;

public class LocalFileHeader extends AbstractFileHeader {

  private byte[] extraField;
  private long offsetStartOfData;
  private boolean writeCompressedSizeInZip64ExtraRecord;

  public LocalFileHeader() {
    setSignature(HeaderSignature.LOCAL_FILE_HEADER);
  }

  public boolean isWriteCompressedSizeInZip64ExtraRecord() {
    return writeCompressedSizeInZip64ExtraRecord;
  }

  public void setWriteCompressedSizeInZip64ExtraRecord(boolean writeCompressedSizeInZip64ExtraRecord) {
    this.writeCompressedSizeInZip64ExtraRecord = writeCompressedSizeInZip64ExtraRecord;
  }
}

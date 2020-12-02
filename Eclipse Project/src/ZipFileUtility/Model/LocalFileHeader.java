package ZipFileUtility.Model;

import ZipFileUtility.Headers.HeaderSignature;

public class LocalFileHeader extends AbstractFileHeader {

    private byte[] extraField;
    private long offsetStartOfData;
    private boolean writeCompressedSizeInZip64ExtraRecord;

    public LocalFileHeader() {
        setSignature(HeaderSignature.LOCAL_FILE_HEADER);
    }

    public void setWriteCompressedSizeInZip64ExtraRecord(boolean writeCompressedSizeInZip64ExtraRecord) {
        this.writeCompressedSizeInZip64ExtraRecord = writeCompressedSizeInZip64ExtraRecord;
    }

    @Override
    public void setSignature(HeaderSignature localFileHeader) {

    }
}

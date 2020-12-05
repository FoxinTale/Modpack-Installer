package ZipFileUtility.Model;

import ZipFileUtility.Headers.HeaderSignature;

public class LocalFileHeader extends AbstractFileHeader {
    private byte[] extraField;
    private long offsetStartOfData;
    public LocalFileHeader() {
        setSignature(HeaderSignature.LOCAL_FILE_HEADER);
    }

    public void setWriteCompressedSizeInZip64ExtraRecord() {
    }

    @Override
    public void setSignature(HeaderSignature localFileHeader) {

    }
}

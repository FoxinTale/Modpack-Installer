package ZipFileUtility.Model;

import ZipFileUtility.Headers.HeaderSignature;

public class FileHeader extends AbstractFileHeader {

    private int versionMadeBy;
    private int fileCommentLength = 0;
    private int diskNumberStart;
    private byte[] internalFileAttributes;
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

    public void setFileCommentLength(int fileCommentLength) {
        this.fileCommentLength = fileCommentLength;
    }

    public int getDiskNumberStart() {
        return diskNumberStart;
    }

    public void setDiskNumberStart(int diskNumberStart) {
        this.diskNumberStart = diskNumberStart;
    }

    public byte[] getInternalFileAttributes() {
        return internalFileAttributes;
    }

    public void setInternalFileAttributes(byte[] internalFileAttributes) {
        this.internalFileAttributes = internalFileAttributes;
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
}

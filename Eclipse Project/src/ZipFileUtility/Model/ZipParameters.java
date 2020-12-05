package ZipFileUtility.Model;

public class ZipParameters {
    public enum SymbolicLinkAction {
        INCLUDE_LINKED_FILE_ONLY,
        INCLUDE_LINK_AND_LINKED_FILE
    }

    private boolean readHiddenFolders = true;
    private final String defaultFolderPath;
    private final String fileNameInZip;
    private final String rootFolderNameInZip;
    private boolean unixMode;

    public ZipParameters(String defaultFolderPath, String fileNameInZip, String rootFolderNameInZip, boolean unixMode) {
        this.defaultFolderPath = defaultFolderPath;
        this.fileNameInZip = fileNameInZip;
        this.rootFolderNameInZip = rootFolderNameInZip;
        this.unixMode = unixMode;
    }

    public void setCompressionMethod() {
    }

    public boolean isReadHiddenFolders() {
        return readHiddenFolders;
    }

    public void setReadHiddenFolders(boolean readHiddenFolders) {
        this.readHiddenFolders = readHiddenFolders;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getDefaultFolderPath() {
        return defaultFolderPath;
    }

    public String getFileNameInZip() {
        return fileNameInZip;
    }

    public void setEntrySize() {
    }

    public String getRootFolderNameInZip() {
        return rootFolderNameInZip;
    }


}

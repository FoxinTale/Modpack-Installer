package ZipFileUtility.Model;

public class ZipParameters {

  public enum SymbolicLinkAction {
    INCLUDE_LINKED_FILE_ONLY,
    INCLUDE_LINK_AND_LINKED_FILE
  };

  private CompressionMethod compressionMethod = CompressionMethod.DEFLATE;
  private boolean readHiddenFolders = true;
  private long entryCRC;
  private String defaultFolderPath;
  private String fileNameInZip;
  private long entrySize = -1;
  private String rootFolderNameInZip;
  private ExcludeFileFilter excludeFileFilter;
  private boolean unixMode;

  public ZipParameters(long entryCRC, String defaultFolderPath, String fileNameInZip, String rootFolderNameInZip, ExcludeFileFilter excludeFileFilter, boolean unixMode) {
    this.entryCRC = entryCRC;
    this.defaultFolderPath = defaultFolderPath;
    this.fileNameInZip = fileNameInZip;
    this.rootFolderNameInZip = rootFolderNameInZip;
    this.excludeFileFilter = excludeFileFilter;
    this.unixMode = unixMode;
  }


  public CompressionMethod getCompressionMethod() {
    return compressionMethod;
  }
  public void setCompressionMethod(CompressionMethod compressionMethod) {
    this.compressionMethod = compressionMethod;
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


  public long getEntrySize() {
    return entrySize;
  }

  public void setEntrySize(long entrySize) {
    this.entrySize = entrySize;
  }


  public String getRootFolderNameInZip() {
    return rootFolderNameInZip;
  }


  public boolean isUnixMode() {
    return unixMode;
  }

}

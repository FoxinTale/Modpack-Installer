package zip4j.model;

import zip4j.model.enums.*;

/**
 * Encapsulates the parameters that that control how Zip4J encodes data
 */
public class ZipParameters {

  /**
   * Indicates the action to take when a symbolic link is added to the ZIP file
   */
  public enum SymbolicLinkAction {
    /**
     * Add only the symbolic link itself, not the target file or its contents
     */
    INCLUDE_LINK_ONLY,
    /**
     * Add only the target file and its contents, using the filename of the symbolic link
     */
    INCLUDE_LINKED_FILE_ONLY,
    /**
     * Add the symbolic link itself and the target file with its original filename and its contents
     */
    INCLUDE_LINK_AND_LINKED_FILE
  };

  private CompressionMethod compressionMethod = CompressionMethod.DEFLATE;
  private CompressionLevel compressionLevel = CompressionLevel.NORMAL;
  private boolean encryptFiles = false;
  private EncryptionMethod encryptionMethod = EncryptionMethod.NONE;
  private boolean readHiddenFiles = true;
  private boolean readHiddenFolders = true;
  private AesKeyStrength aesKeyStrength = AesKeyStrength.KEY_STRENGTH_256;
  private AesVersion aesVersion = AesVersion.TWO;
  private boolean includeRootFolder = true;
  private long entryCRC;
  private String defaultFolderPath;
  private String fileNameInZip;
  private long lastModifiedFileTime = 0;
  private long entrySize = -1;
  private boolean writeExtendedLocalFileHeader = true;
  private boolean overrideExistingFilesInZip = true;
  private String rootFolderNameInZip;
  private String fileComment;
  private SymbolicLinkAction symbolicLinkAction = SymbolicLinkAction.INCLUDE_LINKED_FILE_ONLY;
  private ExcludeFileFilter excludeFileFilter;
  private boolean unixMode;

  /**
   * Create a ZipParameters instance with default values;
   * CompressionMethod.DEFLATE, CompressionLevel.NORMAL, EncryptionMethod.NONE,
   * AesKeyStrength.KEY_STRENGTH_256, AesVerson.Two, SymbolicLinkAction.INCLUDE_LINKED_FILE_ONLY,
   * readHiddenFiles is true, readHiddenFolders is true, includeRootInFolder is true,
   * writeExtendedLocalFileHeader is true, overrideExistingFilesInZip is true
   */
  public ZipParameters() {
  }

  /**
   * Create a clone of given ZipParameters instance
   * @param zipParameters the ZipParameters instance to clone
   */
  public ZipParameters(ZipParameters zipParameters) {
    this.compressionMethod = zipParameters.getCompressionMethod();
    this.compressionLevel = zipParameters.getCompressionLevel();
    this.encryptFiles = zipParameters.isEncryptFiles();
    this.encryptionMethod = zipParameters.getEncryptionMethod();
    this.readHiddenFiles = zipParameters.isReadHiddenFiles();
    this.readHiddenFolders = zipParameters.isReadHiddenFolders();
    this.aesKeyStrength = zipParameters.getAesKeyStrength();
    this.aesVersion = zipParameters.getAesVersion();
    this.includeRootFolder = zipParameters.isIncludeRootFolder();
    this.entryCRC = zipParameters.getEntryCRC();
    this.defaultFolderPath = zipParameters.getDefaultFolderPath();
    this.fileNameInZip = zipParameters.getFileNameInZip();
    this.lastModifiedFileTime = zipParameters.getLastModifiedFileTime();
    this.entrySize = zipParameters.getEntrySize();
    this.writeExtendedLocalFileHeader = zipParameters.isWriteExtendedLocalFileHeader();
    this.overrideExistingFilesInZip = zipParameters.isOverrideExistingFilesInZip();
    this.rootFolderNameInZip = zipParameters.getRootFolderNameInZip();
    this.fileComment = zipParameters.getFileComment();
    this.symbolicLinkAction = zipParameters.getSymbolicLinkAction();
    this.excludeFileFilter = zipParameters.getExcludeFileFilter();
    this.unixMode = zipParameters.isUnixMode();
  }

  /**
   * Get the compression method specified in this ZipParameters
   * @return the ZIP compression method
   */
  public CompressionMethod getCompressionMethod() {
    return compressionMethod;
  }

  /**
   * Set the ZIP compression method
   * @param compressionMethod the ZIP compression method
   */
  public void setCompressionMethod(CompressionMethod compressionMethod) {
    this.compressionMethod = compressionMethod;
  }

  /**
   * Test if files files are to be encrypted
   * @return true if files are to be encrypted
   */
  public boolean isEncryptFiles() {
    return encryptFiles;
  }

  /**
   * Set the flag indicating that files are to be encrypted
   * @param encryptFiles if true, files will be encrypted
   */
public void setEncryptFiles(boolean encryptFiles) {
    this.encryptFiles = encryptFiles;
  }

  /**
   * Get the encryption method used to encrypt files
   * @return the encryption method
   */
  public EncryptionMethod getEncryptionMethod() {
    return encryptionMethod;
  }

  /**
   * Set the encryption method used to encrypt files
   * @param encryptionMethod the encryption method to be used
   */
  public void setEncryptionMethod(EncryptionMethod encryptionMethod) {
    this.encryptionMethod = encryptionMethod;
  }

  /**
   * Get the compression level used to compress files
   * @return the compression level used to compress files
   */
  public CompressionLevel getCompressionLevel() {
    return compressionLevel;
  }

  /**
   * Test if hidden files will be included during folder recursion
   *
   * @return true if hidden files will be included when adding folders to the zip
   */
  public boolean isReadHiddenFiles() {
    return readHiddenFiles;
  }

  /**
   * Test if hidden folders will be included during folder recursion
   *
   * @return true if hidden folders will be included when adding folders to the zip
   */
  public boolean isReadHiddenFolders() {
    return readHiddenFolders;
  }

  /**
   * Get the key strength of the AES encryption key
   * @return the key strength of the AES encryption key
   */
  public AesKeyStrength getAesKeyStrength() {
    return aesKeyStrength;
  }

  /**
   * Get the AES format version used for encryption
   * @return the AES format version used for encryption
   */
  public AesVersion getAesVersion() {
    return aesVersion;
  }

  /**
   * Test if the parent folder of the added files will be included in the ZIP
   * @return true if the parent folder of the added files will be included into the zip
   */
  public boolean isIncludeRootFolder() {
    return includeRootFolder;
  }

  public long getEntryCRC() {
    return entryCRC;
  }

  public String getDefaultFolderPath() {
    return defaultFolderPath;
  }

  public String getFileNameInZip() {
    return fileNameInZip;
  }

  /**
   * Get the last modified time to be used for files written to the ZIP
   * @return the last modified time in milliseconds since the epoch
   */
  public long getLastModifiedFileTime() {
    return lastModifiedFileTime;
  }

  /**
   * Set the last modified time recorded in the ZIP file for the added files.  If less than 0,
   * the last modified time is cleared and the current time is used
   * @param lastModifiedFileTime the last modified time in milliseconds since the epoch
   */
  public void setLastModifiedFileTime(long lastModifiedFileTime) {
    if (lastModifiedFileTime < 0) {
      this.lastModifiedFileTime = 0;
      return;
    }

    this.lastModifiedFileTime = lastModifiedFileTime;
  }

  public long getEntrySize() {
    return entrySize;
  }

  public void setEntrySize(long entrySize) {
    this.entrySize = entrySize;
  }

  public boolean isWriteExtendedLocalFileHeader() {
    return writeExtendedLocalFileHeader;
  }

  public void setWriteExtendedLocalFileHeader(boolean writeExtendedLocalFileHeader) {
    this.writeExtendedLocalFileHeader = writeExtendedLocalFileHeader;
  }

  public boolean isOverrideExistingFilesInZip() {
    return overrideExistingFilesInZip;
  }

  public String getRootFolderNameInZip() {
    return rootFolderNameInZip;
  }

  /**
   * Get the file comment
   * @return the file comment
   */
  public String getFileComment() {
    return fileComment;
  }

  /**
   * Get the behavior when adding a symbolic link
   * @return the behavior when adding a symbolic link
   */
  public SymbolicLinkAction getSymbolicLinkAction() {
    return symbolicLinkAction;
  }

  /**
   * Returns the file exclusion filter that is currently being used when adding files/folders to zip file
   * @return ExcludeFileFilter
   */
  public ExcludeFileFilter getExcludeFileFilter() {
    return excludeFileFilter;
  }

  /**
   * Returns true if zip4j is using unix mode as default. Returns False otherwise.
   * @return true if zip4j is using unix mode as default, false otherwise
   */
  public boolean isUnixMode() {
    return unixMode;
  }

}

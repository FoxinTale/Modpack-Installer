package zip4j;

import zip4j.exception.ZipException;
import zip4j.headers.HeaderReader;
import zip4j.io.inputstream.NumberedSplitRandomAccessFile;
import zip4j.model.UnzipParameters;
import zip4j.model.Zip4jConfig;
import zip4j.model.ZipModel;
import zip4j.model.enums.RandomAccessFileMode;
import zip4j.progress.ProgressMonitor;
import zip4j.tasks.AsyncZipTask;
import zip4j.tasks.ExtractAllFilesTask;
import zip4j.tasks.ExtractAllFilesTask.ExtractAllFilesTaskParameters;
import zip4j.util.FileUtils;
import zip4j.util.InternalZipConstants;
import zip4j.util.Zip4jUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static zip4j.util.FileUtils.isNumberedSplitFile;
import static zip4j.util.InternalZipConstants.CHARSET_UTF_8;
import static zip4j.util.InternalZipConstants.MIN_BUFF_SIZE;
import static zip4j.util.Zip4jUtil.isStringNotNullAndNotEmpty;

/**
 * Base class to handle zip files. Some of the operations supported
 * in this class are:<br>
 * <ul>
 * <li>Create Zip File</li>
 * <li>Add files to zip file</li>
 * <li>Add folder to zip file</li>
 * <li>Extract files from zip files</li>
 * <li>Remove files from zip file</li>
 * </ul>
 */

public class ZipFile implements Closeable {

  private final File zipFile;
  private ZipModel zipModel;
  private boolean isEncrypted;
  private final ProgressMonitor progressMonitor;
  private final boolean runInThread;
  private char[] password;
  private Charset charset = null;
  private ThreadFactory threadFactory;
  private ExecutorService executorService;
  private int bufferSize = InternalZipConstants.BUFF_SIZE;
  private final List<InputStream> openInputStreams = new ArrayList<>();

  /**
   * Creates a new ZipFile instance with the zip file at the location specified in zipFile.
   * This constructor does not yet create a zip file if it does not exist. Creation happens when adding files
   * to this ZipFile instance
   */
  public ZipFile(String zipFile) {
    this(new File(zipFile), null);
  }

  /**
   * Creates a new Zip File Object with the input file.
   * If the zip file does not exist, it is not created at this point.
   *
   * @param zipFile file reference to the zip file
   * @param password password to use for the zip file
   * @throws IllegalArgumentException when zip file parameter is null
   */
  public ZipFile(File zipFile, char[] password) {
    if (zipFile == null) {
      throw new IllegalArgumentException("input zip file parameter is null");
    }

    this.zipFile = zipFile;
    this.password = password;
    this.runInThread = false;
    this.progressMonitor = new ProgressMonitor();
  }

  /**
   * Extracts all the files in the given zip file to the input destination path.
   * If zip file does not exist or destination path is invalid then an
   * exception is thrown.
   *
   * @param destinationPath path to which the entries of the zip are to be extracted
   * @throws ZipException when an issue occurs during extraction
   */
  public void extractAll(String destinationPath) throws ZipException {
    extractAll(destinationPath, new UnzipParameters());
  }

  /**
   * Extracts all entries in the zip file to the destination path considering the options defined in
   * UnzipParameters
   *
   * @param destinationPath path to which the entries of the zip are to be extracted
   * @param unzipParameters parameters to be considered during extraction
   * @throws ZipException when an issue occurs during extraction
   */
  public void extractAll(String destinationPath, UnzipParameters unzipParameters) throws ZipException {
    if (!isStringNotNullAndNotEmpty(destinationPath)) {
      throw new ZipException("output path is null or invalid");
    }

    if (!Zip4jUtil.createDirectoryIfNotExists(new File(destinationPath))) {
      throw new ZipException("invalid output path");
    }

    if (zipModel == null) {
      readZipInfo();
    }

    // Throw an exception if zipModel is still null
    if (zipModel == null) {
      throw new ZipException("Internal error occurred when extracting zip file");
    }

    new ExtractAllFilesTask(zipModel, password, unzipParameters, buildAsyncParameters()).execute(
        new ExtractAllFilesTaskParameters(destinationPath, buildConfig()));
  }

  /**
   * Checks to see if the input zip file is a valid zip file. This method
   * will try to read zip headers. If headers are read successfully, this
   * method returns true else false.
   * <p>
   * Since v2.7.0: if the zip file is a split zip file, this method also checks to see if
   * all the split files of the zip exists.
   *
   * @return boolean - true if a valid zip file, i.e, zip4j is able to read the
   * zip headers, and in case of a split zip file, all split files of the zip exists; false otherwise
   *
   * @since 1.2.3
   */
  public boolean isValidZipFile() {
    if (!zipFile.exists()) {
      return false;
    }

    try {
      readZipInfo();

      return !zipModel.isSplitArchive() || verifyAllSplitFilesOfZipExists(getSplitZipFiles());
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Returns the full file path+names of all split zip files
   * in an ArrayList. For example: If a split zip file(abc.zip) has a 10 split parts
   * this method returns an array list with path + "abc.z01", path + "abc.z02", etc.
   * Returns null if the zip file does not exist
   *
   * @return List of Split zip Files
   */
  public List<File> getSplitZipFiles() throws ZipException {
    readZipInfo();
    return FileUtils.getSplitZipFiles(zipModel);
  }

  /**
   * Closes any open streams that were open by an instance of this class.
   *
   * @throws IOException when the underlying input stream throws an exception when trying to close it
   */
  @Override
  public void close() throws IOException {
    for (InputStream inputStream : openInputStreams) {
      inputStream.close();
    }
    openInputStreams.clear();
  }

  /**
   * Sets a password to be used for the zip file. Will override if a password supplied via ZipFile constructor
   * @param password - char array of the password to be used
   */
  public void setPassword(char[] password) {
    this.password = password;
  }

  /**
   * Returns the size of the buffer used to read streams
   *
   * @return size of the buffer used to read streams
   */
  public int getBufferSize() {
    return bufferSize;
  }

  /**
   * Sets the size of buffer that should be used when reading streams. This size cannot be less than the value defined
   * in InternalZipConstants.MIN_BUFF_SIZE
   *
   * @param bufferSize size of the buffer that should be used when reading streams
   * @throws IllegalArgumentException if bufferSize is less than value configured in InternalZipConstants.MIN_BUFF_SIZE
   */
  public void setBufferSize(int bufferSize) {
    if (bufferSize < MIN_BUFF_SIZE) {
      throw new IllegalArgumentException("Buffer size cannot be less than " + MIN_BUFF_SIZE + " bytes");
    }

    this.bufferSize = bufferSize;
  }

  /**
   * Reads the zip header information for this zip file. If the zip file
   * does not exist, it creates an empty zip model.<br><br>
   * <b>Note:</b> This method does not read local file header information
   *
   */
  private void readZipInfo() throws ZipException {
    if (zipModel != null) {
      return;
    }

    if (!zipFile.exists()) {
      createNewZipModel();
      return;
    }

    if (!zipFile.canRead()) {
      throw new ZipException("no read access for the input zip file");
    }

    try (RandomAccessFile randomAccessFile = initializeRandomAccessFileForHeaderReading()) {
      HeaderReader headerReader = new HeaderReader();
      zipModel = headerReader.readAllHeaders(randomAccessFile, buildConfig());
      zipModel.setZipFile(zipFile);
    } catch (ZipException e) {
      throw e;
    } catch (IOException e) {
      throw new ZipException(e);
    }
  }

  private void createNewZipModel() {
    zipModel = new ZipModel();
    zipModel.setZipFile(zipFile);
  }

  private RandomAccessFile initializeRandomAccessFileForHeaderReading() throws IOException {
    if (isNumberedSplitFile(zipFile)) {
      File[] allSplitFiles = FileUtils.getAllSortedNumberedSplitFiles(zipFile);
      NumberedSplitRandomAccessFile numberedSplitRandomAccessFile =  new NumberedSplitRandomAccessFile(zipFile,
          RandomAccessFileMode.READ.getValue(), allSplitFiles);
      numberedSplitRandomAccessFile.openLastSplitFileForReading();
      return numberedSplitRandomAccessFile;
    }

    return new RandomAccessFile(zipFile, RandomAccessFileMode.READ.getValue());
  }

  private AsyncZipTask.AsyncTaskParameters buildAsyncParameters() {
    if (runInThread) {
      if (threadFactory == null) {
        threadFactory = Executors.defaultThreadFactory();
      }
      executorService = Executors.newSingleThreadExecutor(threadFactory);
    }

    return new AsyncZipTask.AsyncTaskParameters(executorService, runInThread, progressMonitor);
  }

  private boolean verifyAllSplitFilesOfZipExists(List<File> allSplitFiles) {
    for (File splitFile : allSplitFiles) {
      if (!splitFile.exists()) {
        return false;
      }
    }
    return true;
  }

  public ProgressMonitor getProgressMonitor() {
    return progressMonitor;
  }

  public File getFile() {
    return zipFile;
  }

  /**
   * Returns user defined charset that was set by setCharset() method. If no charset was explicitly defined
   * (by calling setCharset()), this method returns the default charset which zip4j uses, which is utf-8.
   *
   * @return user-defined charset or utf-8 if no charset explicitly set
   */
  public Charset getCharset() {
    if (charset == null) {
      return CHARSET_UTF_8;
    }
    return charset;
  }

  /**
   * Sets the charset to be used for encoding file names and comments
   *
   * @param charset charset to use to encode file names and comments
   * @throws IllegalArgumentException if charset is null
   */
  public void setCharset(Charset charset) throws IllegalArgumentException {
    if(charset == null) {
      throw new IllegalArgumentException("charset cannot be null");
    }
    this.charset = charset;
  }

  @Override
  public String toString() {
    return zipFile.toString();
  }

  private Zip4jConfig buildConfig() {
    boolean useUtf8CharsetForPasswords = InternalZipConstants.USE_UTF8_FOR_PASSWORD_ENCODING_DECODING;
    return new Zip4jConfig(charset, bufferSize, useUtf8CharsetForPasswords);
  }

}

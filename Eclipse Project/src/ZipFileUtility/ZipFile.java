package ZipFileUtility;


import ZipFileUtility.Headers.HeaderReader;
import ZipFileUtility.Headers.HeaderUtil;
import ZipFileUtility.Headers.HeaderWriter;
import ZipFileUtility.IO.Input.NumberedSplitRandomAccessFile;
import ZipFileUtility.Model.FileHeader;
import ZipFileUtility.Model.RandomAccessFileMode;
import ZipFileUtility.Model.ZipModel;
import ZipFileUtility.Tasks.AsyncZipTask;
import ZipFileUtility.Tasks.ExtractAllFilesTask;
import ZipFileUtility.Tasks.ExtractFileTask;
import ZipFileUtility.Tasks.SetCommentTask;
import ZipFileUtility.Util.FileUtils;
import ZipFileUtility.Util.InternalZipConstants;
import ZipFileUtility.Util.Zip4jUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ZipFile {

    private final File zipFile;
    private ZipModel zipModel;
    //private boolean isEncrypted;
    private final ProgressMonitor progressMonitor;
    private final boolean runInThread;
    private char[] password;
    private final HeaderWriter headerWriter = new HeaderWriter();
    private Charset charset = InternalZipConstants.CHARSET_UTF_8;
    private ThreadFactory threadFactory;
    private ExecutorService executorService;


    public ZipFile(String zipFile) {
        this(new File(zipFile), null);
    }


/*    public ZipFile(String zipFile, char[] password) {
        this(new File(zipFile), password);
    }


    public ZipFile(File zipFile) {
        this(zipFile, null);
    }*/

    public ZipFile(File zipFile, char[] password) {
        this.zipFile = zipFile;
        this.password = password;
        this.runInThread = false;
        this.progressMonitor = new ProgressMonitor();
    }

/*    public void createSplitZipFile(List<File> filesToAdd, ZipParameters parameters, boolean splitArchive,
                                   long splitLength) throws ZipException {

        if (zipFile.exists()) {
            throw new ZipException("zip file: " + zipFile
                    + " already exists. To add files to existing zip file use addFile method");
        }

        if (filesToAdd == null || filesToAdd.size() == 0) {
            throw new ZipException("input file List is null, cannot create zip file");
        }

        createNewZipModel();
        zipModel.setSplitArchive(splitArchive);
        zipModel.setSplitLength(splitLength);

        new AddFilesToZipTask(zipModel, password, headerWriter, buildAsyncParameters()).execute(
                new AddFilesToZipTask.AddFilesToZipTaskParameters(filesToAdd, parameters, charset));
    }

    public void createSplitZipFileFromFolder(File folderToAdd, ZipParameters parameters, boolean splitArchive,
                                             long splitLength) throws ZipException {
        if (folderToAdd == null) {
            throw new ZipException("folderToAdd is null, cannot create zip file from folder");
        }
        if (parameters == null) {
            throw new ZipException("input parameters are null, cannot create zip file from folder");
        }
        if (zipFile.exists()) {
            throw new ZipException("zip file: " + zipFile
                    + " already exists. To add files to existing zip file use addFolder method");
        }

        createNewZipModel();
        zipModel.setSplitArchive(splitArchive);

        if (splitArchive) {
            zipModel.setSplitLength(splitLength);
        }

        addFolder(folderToAdd, parameters, false);
    }


    public void addFile(String fileToAdd) throws ZipException {
        addFile(fileToAdd, new ZipParameters());
    }*/

/*    public void addFile(String fileToAdd, ZipParameters zipParameters) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileToAdd)) {
            throw new ZipException("file to add is null or empty");
        }
        addFiles(Collections.singletonList(new File(fileToAdd)), zipParameters);
    }


    public void addFile(File fileToAdd) throws ZipException {
        addFiles(Collections.singletonList(fileToAdd), new ZipParameters());
    }

    public void addFile(File fileToAdd, ZipParameters parameters) throws ZipException {
        addFiles(Collections.singletonList(fileToAdd), parameters);
    }

    public void addFiles(List<File> filesToAdd) throws ZipException {
        addFiles(filesToAdd, new ZipParameters());
    }*/


/*    public void addFiles(List<File> filesToAdd, ZipParameters parameters) throws ZipException {

        if (filesToAdd == null || filesToAdd.size() == 0) {
            throw new ZipException("input file List is null or empty");
        }

        if (parameters == null) {
            throw new ZipException("input parameters are null");
        }

        if (progressMonitor.getState() == ProgressMonitor.State.BUSY) {
            throw new ZipException("invalid operation - Zip4j is in busy state");
        }

        readZipInfo();

        if (zipModel == null) {
            throw new ZipException("internal error: zip model is null");
        }

        if (zipFile.exists() && zipModel.isSplitArchive()) {
            throw new ZipException("Zip file already exists. Zip file format does not allow updating split/spanned files");
        }

        new AddFilesToZipTask(zipModel, password, headerWriter, buildAsyncParameters()).execute(
                new AddFilesToZipTask.AddFilesToZipTaskParameters(filesToAdd, parameters, charset));
    }


    public void addFolder(File folderToAdd) throws ZipException {
        addFolder(folderToAdd, new ZipParameters());
    }


    public void addFolder(File folderToAdd, ZipParameters zipParameters) throws ZipException {
        if (folderToAdd == null) {
            throw new ZipException("input path is null, cannot add folder to zip file");
        }

        if (!folderToAdd.exists()) {
            throw new ZipException("folder does not exist");
        }

        if (!folderToAdd.isDirectory()) {
            throw new ZipException("input folder is not a directory");
        }

        if (!folderToAdd.canRead()) {
            throw new ZipException("cannot read input folder");
        }

        if (zipParameters == null) {
            throw new ZipException("input parameters are null, cannot add folder to zip file");
        }

        addFolder(folderToAdd, zipParameters, true);
    }


    private void addFolder(File folderToAdd, ZipParameters zipParameters, boolean checkSplitArchive) throws ZipException {

        readZipInfo();

        if (zipModel == null) {
            throw new ZipException("internal error: zip model is null");
        }

        if (checkSplitArchive) {
            if (zipModel.isSplitArchive()) {
                throw new ZipException("This is a split archive. Zip file format does not allow updating split/spanned files");
            }
        }

        new AddFolderToZipTask(zipModel, password, headerWriter, buildAsyncParameters()).execute(
                new AddFolderToZipTask.AddFolderToZipTaskParameters(folderToAdd, zipParameters, charset));
    }


    public void addStream(InputStream inputStream, ZipParameters parameters) throws ZipException {
        if (inputStream == null) {
            throw new ZipException("inputstream is null, cannot add file to zip");
        }

        if (parameters == null) {
            throw new ZipException("zip parameters are null");
        }

        this.setRunInThread(false);

        readZipInfo();

        if (zipModel == null) {
            throw new ZipException("internal error: zip model is null");
        }

        if (zipFile.exists() && zipModel.isSplitArchive()) {
            throw new ZipException("Zip file already exists. Zip file format does not allow updating split/spanned files");
        }

        new AddStreamToZipTask(zipModel, password, headerWriter, buildAsyncParameters()).execute(
                new AddStreamToZipTask.AddStreamToZipTaskParameters(inputStream, parameters, charset));
    }*/

    public void extractAll(String destinationPath) throws ZipException {

        if (!Zip4jUtil.isStringNotNullAndNotEmpty(destinationPath)) {
            throw new ZipException("output path is null or invalid");
        }

        if (!Zip4jUtil.createDirectoryIfNotExists(new File(destinationPath))) {
            throw new ZipException("invalid output path");
        }

        if (zipModel == null) {
            readZipInfo();
        }

        if (zipModel == null) {
            throw new ZipException("Internal error occurred when extracting zip file");
        }

        if (progressMonitor.getState() == ProgressMonitor.State.BUSY) {
            throw new ZipException("invalid operation - Zip4j is in busy state");
        }

        new ExtractAllFilesTask(zipModel, password, buildAsyncParameters()).execute(
                new ExtractAllFilesTask.ExtractAllFilesTaskParameters(destinationPath, charset));
    }


    public void extractFile(FileHeader fileHeader, String destinationPath) throws ZipException {
        extractFile(fileHeader, destinationPath, null);
    }


    public void extractFile(FileHeader fileHeader, String destinationPath, String newFileName) throws ZipException {
        if (fileHeader == null) {
            throw new ZipException("input file header is null, cannot extract file");
        }

        if (!Zip4jUtil.isStringNotNullAndNotEmpty(destinationPath)) {
            throw new ZipException("destination path is empty or null, cannot extract file");
        }

        if (progressMonitor.getState() == ProgressMonitor.State.BUSY) {
            throw new ZipException("invalid operation - Zip4j is in busy state");
        }

        readZipInfo();

        new ExtractFileTask(zipModel, password, buildAsyncParameters()).execute(
                new ExtractFileTask.ExtractFileTaskParameters(destinationPath, fileHeader, newFileName, charset));
    }


    public void extractFile(String fileName, String destinationPath) throws ZipException {
        extractFile(fileName, destinationPath, null);
    }


    public void extractFile(String fileName, String destinationPath, String newFileName) throws ZipException {

        if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileName)) {
            throw new ZipException("file to extract is null or empty, cannot extract file");
        }

        readZipInfo();

        FileHeader fileHeader = HeaderUtil.getFileHeader(zipModel, fileName);

        if (fileHeader == null) {
            throw new ZipException("No file found with name " + fileName + " in zip file", ZipException.Type.FILE_NOT_FOUND);
        }

        extractFile(fileHeader, destinationPath, newFileName);
    }


    public List<FileHeader> getFileHeaders() throws ZipException {
        readZipInfo();
        if (zipModel == null || zipModel.getCentralDirectory() == null) {
            return Collections.emptyList();
        }
        return zipModel.getCentralDirectory().getFileHeaders();
    }

/*

    public FileHeader getFileHeader(String fileName) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileName)) {
            throw new ZipException("input file name is emtpy or null, cannot get FileHeader");
        }

        readZipInfo();
        if (zipModel == null || zipModel.getCentralDirectory() == null) {
            return null;
        }

        return HeaderUtil.getFileHeader(zipModel, fileName);
    }


    public boolean isEncrypted() throws ZipException {
        if (zipModel == null) {
            readZipInfo();
            if (zipModel == null) {
                throw new ZipException("Zip Model is null");
            }
        }

        if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null) {
            throw new ZipException("invalid zip file");
        }

        for (FileHeader fileHeader : zipModel.getCentralDirectory().getFileHeaders()) {
            if (fileHeader != null) {
                if (fileHeader.isEncrypted()) {
                    isEncrypted = true;
                    break;
                }
            }
        }

        return isEncrypted;
    }
*/


    public boolean isSplitArchive() throws ZipException {

        if (zipModel == null) {
            readZipInfo();
            if (zipModel == null) {
                throw new ZipException("Zip Model is null");
            }
        }

        return zipModel.isSplitArchive();
    }


/*
    public void removeFile(FileHeader fileHeader) throws ZipException {
        if (fileHeader == null) {
            throw new ZipException("input file header is null, cannot remove file");
        }

        removeFile(fileHeader.getFileName());
    }
*/


/*
    public void removeFile(String fileName) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileName)) {
            throw new ZipException("file name is empty or null, cannot remove file");
        }

        removeFiles(Collections.singletonList(fileName));
    }
*/

/*
    public void removeFiles(List<String> fileNames) throws ZipException {
        if (fileNames == null) {
            throw new ZipException("fileNames list is null");
        }

        if (fileNames.isEmpty()) {
            return;
        }

        if (zipModel == null) {
            readZipInfo();
        }

        if (zipModel.isSplitArchive()) {
            throw new ZipException("Zip file format does not allow updating split/spanned files");
        }

        new RemoveFilesFromZipTask(zipModel, headerWriter, buildAsyncParameters()).execute(
                new RemoveFilesFromZipTask.RemoveFilesFromZipTaskParameters(fileNames, charset));
    }


    public void renameFile(FileHeader fileHeader, String newFileName) throws ZipException {
        if (fileHeader == null) {
            throw new ZipException("File header is null");
        }

        renameFile(fileHeader.getFileName(), newFileName);
    }


    public void renameFile(String fileNameToRename, String newFileName) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileNameToRename)) {
            throw new ZipException("file name to be changed is null or empty");
        }

        if (!Zip4jUtil.isStringNotNullAndNotEmpty(newFileName)) {
            throw new ZipException("newFileName is null or empty");
        }

        renameFiles(Collections.singletonMap(fileNameToRename, newFileName));
    }


    public void renameFiles(Map<String, String> fileNamesMap) throws ZipException {
        if (fileNamesMap == null) {
            throw new ZipException("fileNamesMap is null");
        }

        if (fileNamesMap.size() == 0) {
            return;
        }

        readZipInfo();

        if (zipModel.isSplitArchive()) {
            throw new ZipException("Zip file format does not allow updating split/spanned files");
        }

        AsyncZipTask.AsyncTaskParameters asyncTaskParameters = buildAsyncParameters();
        new RenameFilesTask(zipModel, headerWriter, new RawIO(), charset, asyncTaskParameters).execute(
                new RenameFilesTask.RenameFilesTaskParameters(fileNamesMap));
    }


    public void mergeSplitFiles(File outputZipFile) throws ZipException {
        if (outputZipFile == null) {
            throw new ZipException("outputZipFile is null, cannot merge split files");
        }

        if (outputZipFile.exists()) {
            throw new ZipException("output Zip File already exists");
        }

        readZipInfo();

        if (this.zipModel == null) {
            throw new ZipException("zip model is null, corrupt zip file?");
        }

        new MergeSplitZipFileTask(zipModel, buildAsyncParameters()).execute(
                new MergeSplitZipFileTask.MergeSplitZipFileTaskParameters(outputZipFile, charset));
    }*/


    public void setComment(String comment) throws ZipException {
        if (comment == null) {
            throw new ZipException("input comment is null, cannot update zip file");
        }

        if (!zipFile.exists()) {
            throw new ZipException("zip file does not exist, cannot set comment for zip file");
        }

        readZipInfo();

        if (zipModel == null) {
            throw new ZipException("zipModel is null, cannot update zip file");
        }

        if (zipModel.getEndOfCentralDirectoryRecord() == null) {
            throw new ZipException("end of central directory is null, cannot set comment");
        }

        new SetCommentTask(zipModel, buildAsyncParameters()).execute(
                new SetCommentTask.SetCommentTaskTaskParameters(comment, charset));
    }


    public String getComment() throws ZipException {
        if (!zipFile.exists()) {
            throw new ZipException("zip file does not exist, cannot read comment");
        }

        readZipInfo();

        if (zipModel == null) {
            throw new ZipException("zip model is null, cannot read comment");
        }

        if (zipModel.getEndOfCentralDirectoryRecord() == null) {
            throw new ZipException("end of central directory record is null, cannot read comment");
        }

        return zipModel.getEndOfCentralDirectoryRecord().getComment();
    }


/*    public ZipInputStream getInputStream(FileHeader fileHeader) throws IOException {
        if (fileHeader == null) {
            throw new ZipException("FileHeader is null, cannot get InputStream");
        }

        readZipInfo();

        if (zipModel == null) {
            throw new ZipException("zip model is null, cannot get inputstream");
        }

        return UnzipUtil.createZipInputStream(zipModel, fileHeader, password);
    }


    public boolean isValidZipFile() {
        if (!zipFile.exists()) {
            return false;
        }

        try {
            readZipInfo();

            if (zipModel.isSplitArchive() && !verifyAllSplitFilesOfZipExists(getSplitZipFiles())) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }*/


/*    public List<File> getSplitZipFiles() throws ZipException {
        readZipInfo();
        return FileUtils.getSplitZipFiles(zipModel);
    }*/


    public void setPassword(char[] password) {
        this.password = password;
    }


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
            zipModel = headerReader.readAllHeaders(randomAccessFile, charset);
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
        if (FileUtils.isNumberedSplitFile(zipFile)) {
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

/*    private boolean verifyAllSplitFilesOfZipExists(List<File> allSplitFiles) {
        for (File splitFile : allSplitFiles) {
            if (!splitFile.exists()) {
                return false;
            }
        }
        return true;
    }*/

    public ProgressMonitor getProgressMonitor() {
        return progressMonitor;
    }

/*    public boolean isRunInThread() {
        return runInThread;
    }

    public void setRunInThread(boolean runInThread) {
        this.runInThread = runInThread;
    }*/

    public File getFile() {
        return zipFile;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) throws IllegalArgumentException {
        if(charset == null) {
            throw new IllegalArgumentException("charset cannot be null");
        }
        this.charset = charset;
    }

/*    public void setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }*/

    @Override
    public String toString() {
        return zipFile.toString();
    }
}

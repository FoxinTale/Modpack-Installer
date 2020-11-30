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
import ZipFileUtility.Util.FileUtils;
import ZipFileUtility.Util.InternalZipConstants;
import ZipFileUtility.Util.Zip4jUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ZipFile {
    private File zipFile;
    private ZipModel zipModel;
    private ProgressMonitor progressMonitor;
    private boolean runInThread;
    private char[] password;
    private HeaderWriter headerWriter = new HeaderWriter();
    private Charset charset = InternalZipConstants.CHARSET_UTF_8;
    private ThreadFactory threadFactory;
    private ExecutorService executorService;


    public ZipFile(String zipFile) {
        this(new File(zipFile), null);
    }

    public ZipFile(File zipFile, char[] password) {
        this.zipFile = zipFile;
        this.password = password;
        this.runInThread = false;
        this.progressMonitor = new ProgressMonitor();
    }

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


    public boolean isSplitArchive() throws ZipException {

        if (zipModel == null) {
            readZipInfo();
            if (zipModel == null) {
                throw new ZipException("Zip Model is null");
            }
        }

        return zipModel.isSplitArchive();
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


    @Override
    public String toString() {
        return zipFile.toString();
    }
}

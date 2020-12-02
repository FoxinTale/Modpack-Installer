package ZipFileUtility.Tasks;

import ZipFileUtility.Headers.HeaderUtil;
import ZipFileUtility.IO.Input.SplitInputStream;
import ZipFileUtility.IO.Input.ZipInputStream;
import ZipFileUtility.Model.FileHeader;
import ZipFileUtility.Model.ZipModel;
import ZipFileUtility.ProgressMonitor;
import ZipFileUtility.Util.UnzipUtil;

import java.io.IOException;
import java.nio.charset.Charset;

public class ExtractAllFilesTask extends AbstractExtractFileTask<ExtractAllFilesTask.ExtractAllFilesTaskParameters> {

    private char[] password;
    private SplitInputStream splitInputStream;

    public ExtractAllFilesTask(ZipModel zipModel, char[] password, AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, asyncTaskParameters);
        this.password = password;
    }

    @Override
    protected void executeTask(ExtractAllFilesTaskParameters taskParameters, ProgressMonitor progressMonitor)
            throws IOException {
        try (ZipInputStream zipInputStream = prepareZipInputStream(taskParameters.charset)) {
            for (FileHeader fileHeader : getZipModel().getCentralDirectory().getFileHeaders()) {
                if (fileHeader.getFileName().startsWith("__MACOSX")) {
                    progressMonitor.updateWorkCompleted(fileHeader.getUncompressedSize());
                    continue;
                }

                splitInputStream.prepareExtractionForFileHeader(fileHeader);

                extractFile(zipInputStream, fileHeader, taskParameters.outputPath, null, progressMonitor);
                verifyIfTaskIsCancelled();
            }
        } finally {
            if (splitInputStream != null) {
                splitInputStream.close();
            }
        }
    }

    @Override
    protected long calculateTotalWork(ExtractAllFilesTaskParameters taskParameters) {
        return HeaderUtil.getTotalUncompressedSizeOfAllFileHeaders(getZipModel().getCentralDirectory().getFileHeaders());
    }

    private ZipInputStream prepareZipInputStream(Charset charset) throws IOException {
        splitInputStream = UnzipUtil.createSplitInputStream(getZipModel());

        FileHeader fileHeader = getFirstFileHeader(getZipModel());
        if (fileHeader != null) {
            splitInputStream.prepareExtractionForFileHeader(fileHeader);
        }

        return new ZipInputStream(splitInputStream, password, charset);
    }

    private FileHeader getFirstFileHeader(ZipModel zipModel) {
        if (zipModel.getCentralDirectory() == null
                || zipModel.getCentralDirectory().getFileHeaders() == null
                || zipModel.getCentralDirectory().getFileHeaders().size() == 0) {
            return null;
        }

        return zipModel.getCentralDirectory().getFileHeaders().get(0);
    }

    public static class ExtractAllFilesTaskParameters extends AbstractZipTaskParameters {
        private final String outputPath;

        public ExtractAllFilesTaskParameters(String outputPath, Charset charset) {
            super(charset);
            this.outputPath = outputPath;
        }
    }

}

package ZipFileUtility.Tasks;

import ZipFileUtility.Headers.HeaderUtil;
import ZipFileUtility.IO.Input.SplitInputStream;
import ZipFileUtility.IO.Input.ZipInputStream;
import ZipFileUtility.Model.FileHeader;
import ZipFileUtility.Model.ZipModel;
import ZipFileUtility.ProgressMonitor;
import ZipFileUtility.Util.InternalZipConstants;
import ZipFileUtility.Util.UnzipUtil;
import ZipFileUtility.Util.Zip4jUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

public class ExtractFileTask extends AbstractExtractFileTask<ExtractFileTask.ExtractFileTaskParameters> {
    private SplitInputStream splitInputStream;
    public ExtractFileTask(ZipModel zipModel, AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, asyncTaskParameters);
    }

    @Override
    protected void executeTask(ExtractFileTaskParameters taskParameters, ProgressMonitor progressMonitor)
            throws IOException {

        try (ZipInputStream zipInputStream = createZipInputStream(taskParameters.fileHeader, taskParameters.charset)) {
            List<FileHeader> fileHeadersUnderDirectory = getFileHeadersToExtract(taskParameters.fileHeader);
            for (FileHeader fileHeader : fileHeadersUnderDirectory) {
                String newFileName = determineNewFileName(taskParameters.newFileName, taskParameters.fileHeader, fileHeader);
                extractFile(zipInputStream, fileHeader, taskParameters.outputPath, newFileName, progressMonitor);
            }
        } finally {
            if (splitInputStream != null) {
                splitInputStream.close();
            }
        }
    }

    @Override
    protected long calculateTotalWork(ExtractFileTaskParameters taskParameters) {
        List<FileHeader> fileHeadersUnderDirectory = getFileHeadersToExtract(taskParameters.fileHeader);
        return HeaderUtil.getTotalUncompressedSizeOfAllFileHeaders(fileHeadersUnderDirectory);
    }

    private List<FileHeader> getFileHeadersToExtract(FileHeader rootFileHeader) {
        if (!rootFileHeader.isDirectory()) {
            return Collections.singletonList(rootFileHeader);
        }

        return HeaderUtil.getFileHeadersUnderDirectory(
                getZipModel().getCentralDirectory().getFileHeaders(), rootFileHeader);
    }

    private ZipInputStream createZipInputStream(FileHeader fileHeader, Charset charset) throws IOException {
        splitInputStream = UnzipUtil.createSplitInputStream(getZipModel());
        splitInputStream.prepareExtractionForFileHeader(fileHeader);
        return new ZipInputStream(splitInputStream, charset);
    }

    private String determineNewFileName(String newFileName, FileHeader fileHeaderToExtract, FileHeader fileHeaderBeingExtracted) {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(newFileName)) {
            return newFileName;
        }

        if (!fileHeaderToExtract.isDirectory()) {
            return newFileName;
        }

        String fileSeparator = InternalZipConstants.ZIP_FILE_SEPARATOR;
        if (newFileName.endsWith(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
            fileSeparator = "";
        }

        return fileHeaderBeingExtracted.getFileName().replaceFirst(fileHeaderToExtract.getFileName(),
                newFileName + fileSeparator);
    }

    public static class ExtractFileTaskParameters extends AbstractZipTaskParameters {
        private final String outputPath;
        private final FileHeader fileHeader;
        private final String newFileName;

        public ExtractFileTaskParameters(String outputPath, FileHeader fileHeader, String newFileName, Charset charset) {
            super(charset);
            this.outputPath = outputPath;
            this.fileHeader = fileHeader;
            this.newFileName = newFileName;
        }
    }
}

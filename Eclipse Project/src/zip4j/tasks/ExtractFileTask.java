package zip4j.tasks;

import zip4j.exception.ZipException;
import zip4j.headers.HeaderUtil;
import zip4j.io.inputstream.SplitFileInputStream;
import zip4j.io.inputstream.ZipInputStream;
import zip4j.model.FileHeader;
import zip4j.model.UnzipParameters;
import zip4j.model.Zip4jConfig;
import zip4j.model.ZipModel;
import zip4j.progress.ProgressMonitor;
import zip4j.tasks.ExtractFileTask.ExtractFileTaskParameters;
import zip4j.util.FileUtils;
import zip4j.util.InternalZipConstants;
import zip4j.util.UnzipUtil;
import zip4j.util.Zip4jUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static zip4j.exception.ZipException.Type.FILE_NOT_FOUND;
import static zip4j.headers.HeaderUtil.getFileHeadersUnderDirectory;
import static zip4j.headers.HeaderUtil.getTotalUncompressedSizeOfAllFileHeaders;

public class ExtractFileTask extends AbstractExtractFileTask<ExtractFileTaskParameters> {

  private final char[] password;
  private SplitFileInputStream splitInputStream;

  public ExtractFileTask(ZipModel zipModel, char[] password, UnzipParameters unzipParameters,
                         AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
    super(zipModel, unzipParameters, asyncTaskParameters);
    this.password = password;
  }

  @Override
  protected void executeTask(ExtractFileTaskParameters taskParameters, ProgressMonitor progressMonitor)
      throws IOException {

    List<FileHeader> fileHeadersUnderDirectory = getFileHeadersToExtract(taskParameters.fileToExtract);
    try(ZipInputStream zipInputStream = createZipInputStream(taskParameters.zip4jConfig)) {
      byte[] readBuff = new byte[taskParameters.zip4jConfig.getBufferSize()];
      for (FileHeader fileHeader : fileHeadersUnderDirectory) {
        splitInputStream.prepareExtractionForFileHeader(fileHeader);
        String newFileName = determineNewFileName(taskParameters.newFileName, taskParameters.fileToExtract, fileHeader);
        extractFile(zipInputStream, fileHeader, taskParameters.outputPath, newFileName, progressMonitor, readBuff);
      }
    } finally {
      if (splitInputStream != null) {
        splitInputStream.close();
      }
    }
  }

  @Override
  protected long calculateTotalWork(ExtractFileTaskParameters taskParameters) throws ZipException {
    List<FileHeader> fileHeadersUnderDirectory = getFileHeadersToExtract(taskParameters.fileToExtract);
    return getTotalUncompressedSizeOfAllFileHeaders(fileHeadersUnderDirectory);
  }

  private List<FileHeader> getFileHeadersToExtract(String fileNameToExtract) throws ZipException {
    if (!FileUtils.isZipEntryDirectory(fileNameToExtract)) {
      FileHeader fileHeader = HeaderUtil.getFileHeader(getZipModel(), fileNameToExtract);
      if (fileHeader == null) {
        throw new ZipException("No file found with name " + fileNameToExtract + " in zip file", FILE_NOT_FOUND);
      }
      return Collections.singletonList(fileHeader);
    }

    return getFileHeadersUnderDirectory(getZipModel().getCentralDirectory().getFileHeaders(), fileNameToExtract);
  }

  private ZipInputStream createZipInputStream(Zip4jConfig zip4jConfig) throws IOException {
    splitInputStream = UnzipUtil.createSplitInputStream(getZipModel());
    return new ZipInputStream(splitInputStream, password, zip4jConfig);
  }

  private String determineNewFileName(String newFileName, String fileNameToExtract,
                                      FileHeader fileHeaderBeingExtracted) {
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(newFileName)) {
      return newFileName;
    }

    if (!FileUtils.isZipEntryDirectory(fileNameToExtract)) {
      return newFileName;
    }

    String fileSeparator = InternalZipConstants.ZIP_FILE_SEPARATOR;
    if (newFileName.endsWith(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
      fileSeparator = "";
    }

    return fileHeaderBeingExtracted.getFileName().replaceFirst(fileNameToExtract, newFileName + fileSeparator);
  }

  public static class ExtractFileTaskParameters extends AbstractZipTaskParameters {
    private final String outputPath;
    private final String fileToExtract;
    private final String newFileName;

    public ExtractFileTaskParameters(String outputPath, String fileToExtract, String newFileName,
                                     Zip4jConfig zip4jConfig) {
      super(zip4jConfig);
      this.outputPath = outputPath;
      this.fileToExtract = fileToExtract;
      this.newFileName = newFileName;
    }
  }
}

package ZipFileUtility.Tasks;


/*
import ZipFileUtility.Headers.HeaderUtil;
import ZipFileUtility.Headers.HeaderWriter;
import ZipFileUtility.IO.Output.SplitOutputStream;
import ZipFileUtility.IO.Output.ZipOutputStream;
import ZipFileUtility.Model.CompressionMethod;
import ZipFileUtility.Model.FileHeader;
import ZipFileUtility.Model.ZipModel;
import ZipFileUtility.Model.ZipParameters;
import ZipFileUtility.ProgressMonitor;
import ZipFileUtility.Util.InternalZipConstants;
import ZipFileUtility.Util.Zip4jUtil;
import ZipFileUtility.ZipException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
*/

public class AddStreamToZipTask{
/*   extends AbstractAddFileToZipTask<AddStreamToZipTask.AddStreamToZipTaskParameters>
  public AddStreamToZipTask(ZipModel zipModel, char[] password, HeaderWriter headerWriter, AsyncTaskParameters asyncTaskParameters) {
    super(zipModel, password, headerWriter, asyncTaskParameters);
  }

  @Override
  protected void executeTask(AddStreamToZipTaskParameters taskParameters, ProgressMonitor progressMonitor)
      throws IOException {

    verifyZipParameters(taskParameters.zipParameters);

    if (!Zip4jUtil.isStringNotNullAndNotEmpty(taskParameters.zipParameters.getFileNameInZip())) {
      throw new ZipException("fileNameInZip has to be set in zipParameters when adding stream");
    }

    removeFileIfExists(getZipModel(), taskParameters.charset, taskParameters.zipParameters.getFileNameInZip(), progressMonitor);

    // For streams, it is necessary to write extended local file header because of Zip standard encryption.
    // If we do not write extended local file header, zip standard encryption needs a crc upfront for key,
    // which cannot be calculated until we read the complete stream. If we use extended local file header,
    // last modified file time is used, or current system time if not available.
    taskParameters.zipParameters.setWriteExtendedLocalFileHeader(true);

    if (taskParameters.zipParameters.getCompressionMethod().equals(CompressionMethod.STORE)) {
      // Set some random value here. This will be updated again when closing entry
      taskParameters.zipParameters.setEntrySize(0);
    }

    try(SplitOutputStream splitOutputStream = new SplitOutputStream(getZipModel().getZipFile(), getZipModel().getSplitLength());
        ZipOutputStream zipOutputStream = initializeOutputStream(splitOutputStream, taskParameters.charset)) {

      byte[] readBuff = new byte[InternalZipConstants.BUFF_SIZE];
      int readLen = -1;

      ZipParameters zipParameters = taskParameters.zipParameters;
      zipOutputStream.putNextEntry(zipParameters);

      if (!zipParameters.getFileNameInZip().endsWith("/") &&
          !zipParameters.getFileNameInZip().endsWith("\\")) {
        while ((readLen = taskParameters.inputStream.read(readBuff)) != -1) {
          zipOutputStream.write(readBuff, 0, readLen);
        }
      }

      FileHeader fileHeader = zipOutputStream.closeEntry();

      if (fileHeader.getCompressionMethod().equals(CompressionMethod.STORE)) {
        updateLocalFileHeader(fileHeader, splitOutputStream);
      }
    }
  }

  @Override
  protected long calculateTotalWork(AddStreamToZipTaskParameters taskParameters) {
    return 0;
  }

  private void removeFileIfExists(ZipModel zipModel, Charset charset, String fileNameInZip, ProgressMonitor progressMonitor)
      throws ZipException {

    FileHeader fileHeader = HeaderUtil.getFileHeader(zipModel, fileNameInZip);
    if (fileHeader  != null) {
      removeFile(fileHeader, progressMonitor, charset);
    }
  }

  public static class AddStreamToZipTaskParameters extends AbstractZipTaskParameters {
    private InputStream inputStream;
    private ZipParameters zipParameters;

    public AddStreamToZipTaskParameters(InputStream inputStream, ZipParameters zipParameters, Charset charset) {
      super(charset);
      this.inputStream = inputStream;
      this.zipParameters = zipParameters;
    }
  }*/
}

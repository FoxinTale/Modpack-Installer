package zip4j.tasks;

import zip4j.exception.ZipException;
import zip4j.headers.HeaderWriter;
import zip4j.io.outputstream.SplitOutputStream;
import zip4j.model.EndOfCentralDirectoryRecord;
import zip4j.model.Zip4jConfig;
import zip4j.model.ZipModel;
import zip4j.progress.ProgressMonitor;

import java.io.IOException;

public class SetCommentTask extends AsyncZipTask<SetCommentTask.SetCommentTaskTaskParameters> {

  private final ZipModel zipModel;

  public SetCommentTask(ZipModel zipModel, AsyncTaskParameters asyncTaskParameters) {
    super(asyncTaskParameters);
    this.zipModel = zipModel;
  }

  @Override
  protected void executeTask(SetCommentTaskTaskParameters taskParameters, ProgressMonitor progressMonitor) throws IOException {
    if (taskParameters.comment == null) {
      throw new ZipException("comment is null, cannot update Zip file with comment");
    }

    EndOfCentralDirectoryRecord endOfCentralDirectoryRecord = zipModel.getEndOfCentralDirectoryRecord();
    endOfCentralDirectoryRecord.setComment(taskParameters.comment);

    try (SplitOutputStream outputStream = new SplitOutputStream(zipModel.getZipFile())) {
      if (zipModel.isZip64Format()) {
        outputStream.seek(zipModel.getZip64EndOfCentralDirectoryRecord()
            .getOffsetStartCentralDirectoryWRTStartDiskNumber());
      } else {
        outputStream.seek(endOfCentralDirectoryRecord.getOffsetOfStartOfCentralDirectory());
      }

      HeaderWriter headerWriter = new HeaderWriter();
      headerWriter.finalizeZipFileWithoutValidations(zipModel, outputStream, taskParameters.zip4jConfig.getCharset());
    }
  }

  @Override
  protected long calculateTotalWork(SetCommentTaskTaskParameters taskParameters) {
    return 0;
  }

  @Override
  protected ProgressMonitor.Task getTask() {
    return ProgressMonitor.Task.SET_COMMENT;
  }

  public static class SetCommentTaskTaskParameters extends AbstractZipTaskParameters {
    private String comment;

    public SetCommentTaskTaskParameters(String comment, Zip4jConfig zip4jConfig) {
      super(zip4jConfig);
      this.comment = comment;
    }
  }
}

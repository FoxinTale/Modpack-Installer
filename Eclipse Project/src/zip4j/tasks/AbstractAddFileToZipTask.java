package zip4j.tasks;

import zip4j.exception.ZipException;
import zip4j.headers.HeaderUtil;
import zip4j.headers.HeaderWriter;
import zip4j.io.outputstream.SplitOutputStream;
import zip4j.io.outputstream.ZipOutputStream;
import zip4j.model.FileHeader;
import zip4j.model.Zip4jConfig;
import zip4j.model.ZipModel;
import zip4j.model.ZipParameters;
import zip4j.progress.ProgressMonitor;
import zip4j.tasks.RemoveFilesFromZipTask.RemoveFilesFromZipTaskParameters;

import java.io.IOException;
import java.util.Collections;

import static zip4j.model.enums.CompressionMethod.DEFLATE;
import static zip4j.model.enums.CompressionMethod.STORE;
import static zip4j.model.enums.EncryptionMethod.NONE;

public abstract class AbstractAddFileToZipTask<T> extends AsyncZipTask<T> {

  private final ZipModel zipModel;
  private final char[] password;
  private final HeaderWriter headerWriter;

  AbstractAddFileToZipTask(ZipModel zipModel, char[] password, HeaderWriter headerWriter,
                           AsyncTaskParameters asyncTaskParameters) {
    super(asyncTaskParameters);
    this.zipModel = zipModel;
    this.password = password;
    this.headerWriter = headerWriter;

  }

  ZipOutputStream initializeOutputStream(SplitOutputStream splitOutputStream, Zip4jConfig zip4jConfig) throws IOException {
    if (zipModel.getZipFile().exists()) {
      splitOutputStream.seek(HeaderUtil.getOffsetStartOfCentralDirectory(zipModel));
    }

    return new ZipOutputStream(splitOutputStream, password, zip4jConfig, zipModel);
  }

  void verifyZipParameters(ZipParameters parameters) throws ZipException {
    if (parameters == null) {
      throw new ZipException("cannot validate zip parameters");
    }

    if (parameters.getCompressionMethod() != STORE && parameters.getCompressionMethod() != DEFLATE) {
      throw new ZipException("unsupported compression type");
    }

    if (parameters.isEncryptFiles()) {
      if (parameters.getEncryptionMethod() == NONE) {
        throw new ZipException("Encryption method has to be set, when encrypt files flag is set");
      }

      if (password == null || password.length <= 0) {
        throw new ZipException("input password is empty or null");
      }
    } else {
      parameters.setEncryptionMethod(NONE);
    }
  }

  void updateLocalFileHeader(FileHeader fileHeader, SplitOutputStream splitOutputStream) throws IOException {
    headerWriter.updateLocalFileHeader(fileHeader, getZipModel(), splitOutputStream);
  }

  void removeFile(FileHeader fileHeader, ProgressMonitor progressMonitor, Zip4jConfig zip4jConfig) throws ZipException {
    AsyncTaskParameters asyncTaskParameters = new AsyncTaskParameters(null, false, progressMonitor);
    RemoveFilesFromZipTask removeFilesFromZipTask = new RemoveFilesFromZipTask(zipModel, headerWriter, asyncTaskParameters);
    RemoveFilesFromZipTaskParameters parameters = new RemoveFilesFromZipTaskParameters(
            Collections.singletonList(fileHeader.getFileName()), zip4jConfig);
    removeFilesFromZipTask.execute(parameters);
  }


  @Override
  protected ProgressMonitor.Task getTask() {
    return ProgressMonitor.Task.ADD_ENTRY;
  }

  protected ZipModel getZipModel() {
    return zipModel;
  }
}

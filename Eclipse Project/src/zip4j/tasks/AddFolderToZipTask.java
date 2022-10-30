package zip4j.tasks;


import zip4j.exception.ZipException;
import zip4j.headers.HeaderWriter;
import zip4j.model.Zip4jConfig;
import zip4j.model.ZipModel;
import zip4j.model.ZipParameters;
import zip4j.progress.ProgressMonitor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static zip4j.util.FileUtils.getFilesInDirectoryRecursive;

public class AddFolderToZipTask extends AbstractAddFileToZipTask<AddFolderToZipTask.AddFolderToZipTaskParameters> {

  public AddFolderToZipTask(ZipModel zipModel, char[] password, HeaderWriter headerWriter, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
    super(zipModel, password, headerWriter, asyncTaskParameters);
  }

  @Override
  protected void executeTask(AddFolderToZipTaskParameters taskParameters, ProgressMonitor progressMonitor)
      throws IOException {
    List<File> filesToAdd = getFilesToAdd(taskParameters);
    setDefaultFolderPath(taskParameters);
    addFilesToZip(filesToAdd, progressMonitor, taskParameters.zipParameters, taskParameters.zip4jConfig);
  }

  @Override
  protected long calculateTotalWork(AddFolderToZipTaskParameters taskParameters) throws ZipException {
    List<File> filesToAdd = getFilesToAdd(taskParameters);

    if (taskParameters.zipParameters.isIncludeRootFolder()) {
      filesToAdd.add(taskParameters.folderToAdd);
    }

    return calculateWorkForFiles(filesToAdd, taskParameters.zipParameters);
  }

  private void setDefaultFolderPath(AddFolderToZipTaskParameters taskParameters) throws IOException {
    String rootFolderPath;
    File folderToAdd = taskParameters.folderToAdd;
    if (taskParameters.zipParameters.isIncludeRootFolder()) {
      File parentFile = folderToAdd.getCanonicalFile().getParentFile();
      if (parentFile == null) {
        rootFolderPath = folderToAdd.getCanonicalPath();
      } else {
        rootFolderPath = folderToAdd.getCanonicalFile().getParentFile().getCanonicalPath();
      }
    } else {
      rootFolderPath = folderToAdd.getCanonicalPath();
    }

    taskParameters.zipParameters.setDefaultFolderPath(rootFolderPath);
  }

  private List<File> getFilesToAdd(AddFolderToZipTaskParameters taskParameters) throws ZipException {
    List<File> filesToAdd = getFilesInDirectoryRecursive(taskParameters.folderToAdd, taskParameters.zipParameters);

    if (taskParameters.zipParameters.isIncludeRootFolder()) {
      filesToAdd.add(taskParameters.folderToAdd);
    }

    return filesToAdd;
  }

  public static class AddFolderToZipTaskParameters extends AbstractZipTaskParameters {
    private final File folderToAdd;
    private final ZipParameters zipParameters;

    public AddFolderToZipTaskParameters(File folderToAdd, ZipParameters zipParameters, Zip4jConfig zip4jConfig) {
      super(zip4jConfig);
      this.folderToAdd = folderToAdd;
      this.zipParameters = zipParameters;
    }
  }

}

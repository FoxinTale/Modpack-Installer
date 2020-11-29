package ZipFileUtility.Util;

import ZipFileUtility.IO.Input.NumberedSplitInputStream;
import ZipFileUtility.IO.Input.SplitInputStream;
import ZipFileUtility.IO.Input.ZipStandardSplitInputStream;
import ZipFileUtility.Model.FileHeader;
import ZipFileUtility.Model.ZipModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class UnzipUtil {

/*
  public static ZipInputStream createZipInputStream(ZipModel zipModel, FileHeader fileHeader, char[] password)
      throws IOException {

    SplitInputStream splitInputStream = null;
    try {
      splitInputStream = createSplitInputStream(zipModel);
      splitInputStream.prepareExtractionForFileHeader(fileHeader);

      ZipInputStream zipInputStream = new ZipInputStream(splitInputStream, password);
      if (zipInputStream.getNextEntry(fileHeader) == null) {
        throw new ZipException("Could not locate local file header for corresponding file header");
      }

      return zipInputStream;
    } catch (IOException e) {
      if (splitInputStream != null) {
        splitInputStream.close();
      }
      throw e;
    }
  }
*/

  public static void applyFileAttributes(FileHeader fileHeader, File file) {

    try {
      Path path = file.toPath();
      FileUtils.setFileAttributes(path, fileHeader.getExternalFileAttributes());
      FileUtils.setFileLastModifiedTime(path, fileHeader.getLastModifiedTime());
    } catch (NoSuchMethodError e) {
      FileUtils.setFileLastModifiedTimeWithoutNio(file, fileHeader.getLastModifiedTime());
    }
  }

  public static SplitInputStream createSplitInputStream(ZipModel zipModel) throws IOException {
    File zipFile = zipModel.getZipFile();

    if (zipFile.getName().endsWith(InternalZipConstants.SEVEN_ZIP_SPLIT_FILE_EXTENSION_PATTERN)) {
      return new NumberedSplitInputStream(zipModel.getZipFile(), true,
          zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
    }

    return new ZipStandardSplitInputStream(zipModel.getZipFile(), zipModel.isSplitArchive(),
        zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
  }

}

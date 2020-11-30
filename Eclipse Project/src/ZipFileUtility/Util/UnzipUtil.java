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

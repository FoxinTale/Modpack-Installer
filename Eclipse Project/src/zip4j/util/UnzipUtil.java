package zip4j.util;


import zip4j.io.inputstream.NumberedSplitFileInputStream;
import zip4j.io.inputstream.SplitFileInputStream;
import zip4j.io.inputstream.ZipStandardSplitFileInputStream;
import zip4j.model.FileHeader;
import zip4j.model.ZipModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static zip4j.util.FileUtils.*;

public class UnzipUtil {

  public static void applyFileAttributes(FileHeader fileHeader, File file) {

    try {
      Path path = file.toPath();
      setFileAttributes(path, fileHeader.getExternalFileAttributes());
      setFileLastModifiedTime(path, fileHeader.getLastModifiedTime());
    } catch (NoSuchMethodError e) {
      setFileLastModifiedTimeWithoutNio(file, fileHeader.getLastModifiedTime());
    }
  }

  public static SplitFileInputStream createSplitInputStream(ZipModel zipModel) throws IOException {
    File zipFile = zipModel.getZipFile();

    if (zipFile.getName().endsWith(InternalZipConstants.SEVEN_ZIP_SPLIT_FILE_EXTENSION_PATTERN)) {
      return new NumberedSplitFileInputStream(zipModel.getZipFile());
    }

    return new ZipStandardSplitFileInputStream(zipModel.getZipFile(), zipModel.isSplitArchive(),
        zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
  }

}

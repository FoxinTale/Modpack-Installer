package ZipFileUtility.IO.Input;

import ZipFileUtility.Util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NumberedSplitInputStream extends SplitInputStream {

  public NumberedSplitInputStream(File zipFile, boolean isSplitZipArchive, int lastSplitZipFileNumber)
      throws FileNotFoundException {
    super(zipFile, isSplitZipArchive, lastSplitZipFileNumber);
  }

  @Override
  protected File getNextSplitFile(int zipFileIndex) throws IOException {
    String currZipFileNameWithPath = zipFile.getCanonicalPath();
    String fileNameWithPathAndWithoutExtension = currZipFileNameWithPath.substring(0,
        currZipFileNameWithPath.lastIndexOf("."));
    return new File(fileNameWithPathAndWithoutExtension + FileUtils.getNextNumberedSplitFileCounterAsExtension(zipFileIndex));
  }
}

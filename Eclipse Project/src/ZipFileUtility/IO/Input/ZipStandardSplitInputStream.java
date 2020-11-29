package ZipFileUtility.IO.Input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ZipStandardSplitInputStream extends SplitInputStream {

  private int lastSplitZipFileNumber;

  public ZipStandardSplitInputStream(File zipFile, boolean isSplitZipArchive, int lastSplitZipFileNumber) throws FileNotFoundException {
    super(zipFile, isSplitZipArchive, lastSplitZipFileNumber);
    this.lastSplitZipFileNumber = lastSplitZipFileNumber;
  }

  @Override
  protected File getNextSplitFile(int zipFileIndex) throws IOException {
    if (zipFileIndex == lastSplitZipFileNumber) {
      return zipFile;
    }

    String currZipFileNameWithPath = zipFile.getCanonicalPath();
    String extensionSubString = ".z0";
    if (zipFileIndex >= 9) {
      extensionSubString = ".z";
    }

    return new File(currZipFileNameWithPath.substring(0,
        currZipFileNameWithPath.lastIndexOf(".")) + extensionSubString + (zipFileIndex + 1));
  }
}

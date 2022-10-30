package zip4j.util;

import zip4j.exception.ZipException;
import zip4j.progress.ProgressMonitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;

public class CrcUtil {

  private static final int BUF_SIZE = 1 << 14; //16384

  public static long computeFileCrc(File inputFile, ProgressMonitor progressMonitor) throws IOException {

    if (inputFile == null || !inputFile.exists() || !inputFile.canRead()) {
      throw new ZipException("input file is null or does not exist or cannot read. " +
          "Cannot calculate CRC for the file");
    }

    byte[] buff = new byte[BUF_SIZE];
    CRC32 crc32 = new CRC32();

    try(InputStream inputStream = new FileInputStream(inputFile)) {
      int readLen;
      while ((readLen = inputStream.read(buff)) != -1) {
        crc32.update(buff, 0, readLen);

        if (progressMonitor != null) {
          progressMonitor.updateWorkCompleted(readLen);
          if (progressMonitor.isCancelAllTasks()) {
            progressMonitor.setResult(ProgressMonitor.Result.CANCELLED);
            progressMonitor.setState(ProgressMonitor.State.READY);
            return 0;
          }
        }
      }
      return crc32.getValue();
    }
  }

}

package ZipFileUtility.IO.Output;

import java.io.IOException;

public interface OutputStreamWithSplitZipSupport {

  long getFilePointer() throws IOException;

  int getCurrentSplitFileCounter();
}

package ZipFileUtility.Model;

import java.io.File;

public interface ExcludeFileFilter {

    boolean isExcluded(File file);

}

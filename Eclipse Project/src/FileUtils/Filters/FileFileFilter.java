package FileUtils.Filters;

import java.io.File;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class FileFileFilter extends AbstractFileFilter implements Serializable {
    public static final IOFileFilter INSTANCE = new FileFileFilter();

    @Deprecated
    public static final IOFileFilter FILE = INSTANCE;
    private static final long serialVersionUID = 5345244090827540862L;

    protected FileFileFilter() {
    }

    @Override
    public boolean accept(final File file) {
        return file.isFile();
    }

    @Override
    public FileVisitResult accept(final Path file, final BasicFileAttributes attributes) {
        return toFileVisitResult(Files.isRegularFile(file));
    }
}

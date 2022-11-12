package FileUtils.Filters;

import java.io.File;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class TrueFileFilter implements IOFileFilter, Serializable {

    private static final String TO_STRING = Boolean.TRUE.toString();
    private static final long serialVersionUID = 8782512160909720199L;
    public static final IOFileFilter TRUE = new TrueFileFilter();
    public static final IOFileFilter INSTANCE = TRUE;

    protected TrueFileFilter() {
    }

    @Override
    public boolean accept(final File file) {
        return true;
    }

    @Override
    public boolean accept(final File dir, final String name) {
        return true;
    }

    @Override
    public FileVisitResult accept(final Path file, final BasicFileAttributes attributes) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public IOFileFilter or(final IOFileFilter fileFilter) {
        return INSTANCE;
    }

    @Override
    public IOFileFilter and(final IOFileFilter fileFilter) {
        return fileFilter;
    }

    @Override
    public String toString() {
        return TO_STRING;
    }
}

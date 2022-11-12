package FileUtils.Filters;

import FileUtils.Filters.AbstractFileFilter;

import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public class FileEqualsFileFilter extends AbstractFileFilter {

    private final File file;
    private final Path path;

    public FileEqualsFileFilter(final File file) {
        this.file = file;
        this.path = file.toPath();
    }

    @Override
    public boolean accept(final File file) {
        return Objects.equals(this.file, file);
    }

    @Override
    public FileVisitResult accept(final Path path, final BasicFileAttributes attributes) {
        return toFileVisitResult(Objects.equals(this.path, path));
    }
}

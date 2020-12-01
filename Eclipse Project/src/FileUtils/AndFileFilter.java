package FileUtils;

import java.io.File;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AndFileFilter extends AbstractFileFilter
        implements ConditionalFileFilter, Serializable {

    private static final long serialVersionUID = 7215974688563965257L;
    private final List<IOFileFilter> fileFilters;

    private AndFileFilter(final ArrayList<IOFileFilter> initialList) {
        this.fileFilters = Objects.requireNonNull(initialList);
    }

    private AndFileFilter(final int initialCapacity) {
        this(new ArrayList<>(initialCapacity));
    }

    public AndFileFilter(final IOFileFilter filter1, final IOFileFilter filter2) {
        this(2);
        addFileFilter(filter1);
        addFileFilter(filter2);
    }

    @Override
    public boolean accept(final File file) {
        if (isEmpty()) {
            return false;
        }
        for (final IOFileFilter fileFilter : fileFilters) {
            if (!fileFilter.accept(file)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean accept(final File file, final String name) {
        if (isEmpty()) {
            return false;
        }
        for (final IOFileFilter fileFilter : fileFilters) {
            if (!fileFilter.accept(file, name)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FileVisitResult accept(final Path file, final BasicFileAttributes attributes) {
        if (isEmpty()) {
            return FileVisitResult.TERMINATE;
        }
        for (final IOFileFilter fileFilter : fileFilters) {
            if (fileFilter.accept(file, attributes) != FileVisitResult.CONTINUE) {
                return FileVisitResult.TERMINATE;
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public void addFileFilter(final IOFileFilter fileFilter) {
        this.fileFilters.add(Objects.requireNonNull(fileFilter, "fileFilter"));
    }

    private boolean isEmpty() {
        return this.fileFilters.isEmpty();
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(super.toString());
        buffer.append("(");
        for (int i = 0; i < fileFilters.size(); i++) {
            if (i > 0) {
                buffer.append(",");
            }
            buffer.append(Objects.toString(fileFilters.get(i)));
        }
        buffer.append(")");
        return buffer.toString();
    }
}

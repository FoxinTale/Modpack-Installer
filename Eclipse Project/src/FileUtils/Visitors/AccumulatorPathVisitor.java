package FileUtils.Visitors;

import FileUtils.Counters;
import FileUtils.Counters.PathCounters;
import FileUtils.Filters.PathFilter;
import FileUtils.PathUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class AccumulatorPathVisitor extends CountingPathVisitor {

    public static AccumulatorPathVisitor withLongCounters() {
        return new AccumulatorPathVisitor(Counters.longPathCounters());
    }

    private final List<Path> dirList = new ArrayList<>();
    private final List<Path> fileList = new ArrayList<>();

    public AccumulatorPathVisitor(final PathCounters pathCounter) {
        super(pathCounter);
    }

    public AccumulatorPathVisitor(final PathCounters pathCounter, final PathFilter fileFilter,
                                  final PathFilter dirFilter) {
        super(pathCounter, fileFilter, dirFilter);
    }

    private void add(final List<Path> list, final Path dir) {
        list.add(dir.normalize());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof AccumulatorPathVisitor)) {
            return false;
        }
        final AccumulatorPathVisitor other = (AccumulatorPathVisitor) obj;
        return Objects.equals(dirList, other.dirList) && Objects.equals(fileList, other.fileList);
    }

    public List<Path> getDirList() {
        return dirList;
    }

    public List<Path> getFileList() {
        return fileList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(dirList, fileList);
        return result;
    }


    public List<Path> relativizeDirectories(final Path parent, final boolean sort,
                                            final Comparator<? super Path> comparator) {
        return PathUtils.relativize(getDirList(), parent, sort, comparator);
    }


    public List<Path> relativizeFiles(final Path parent, final boolean sort,
                                      final Comparator<? super Path> comparator) {
        return PathUtils.relativize(getFileList(), parent, sort, comparator);
    }

    @Override
    protected void updateDirCounter(final Path dir, final IOException exc) {
        super.updateDirCounter(dir, exc);
        add(dirList, dir);
    }

    @Override
    protected void updateFileCounters(final Path file, final BasicFileAttributes attributes) {
        super.updateFileCounters(file, attributes);
        add(fileList, file);
    }
}

package FileUtils.Filters;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

@FunctionalInterface
public interface PathFilter {
    FileVisitResult accept(Path path, BasicFileAttributes attributes);
}

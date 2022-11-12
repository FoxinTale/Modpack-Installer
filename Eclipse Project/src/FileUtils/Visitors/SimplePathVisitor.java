package FileUtils.Visitors;

import FileUtils.Visitors.PathVisitor;

import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;

public abstract class SimplePathVisitor extends SimpleFileVisitor<Path> implements PathVisitor {
    protected SimplePathVisitor() {
    }
}

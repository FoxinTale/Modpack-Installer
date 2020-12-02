package FileUtils;


import FileUtils.Counters.PathCounters;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PathUtils {

    private static class RelativeSortedPaths {

        final boolean equals;
        final List<Path> relativeFileList1;
        final List<Path> relativeFileList2;

        private RelativeSortedPaths(final Path dir1, final Path dir2, final int maxDepth,
                                    final LinkOption[] linkOptions, final FileVisitOption[] fileVisitOptions) throws IOException {
            List<Path> tmpRelativeDirList1 = null;
            List<Path> tmpRelativeDirList2 = null;
            List<Path> tmpRelativeFileList1 = null;
            List<Path> tmpRelativeFileList2 = null;
            if (dir1 == null && dir2 == null) {
                equals = true;
            } else if (dir1 == null ^ dir2 == null) {
                equals = false;
            } else {
                final boolean parentDirExists1 = Files.exists(dir1, linkOptions);
                final boolean parentDirExists2 = Files.exists(dir2, linkOptions);
                if (!parentDirExists1 || !parentDirExists2) {
                    equals = !parentDirExists1 && !parentDirExists2;
                } else {
                    final AccumulatorPathVisitor visitor1 = accumulate(dir1, maxDepth, fileVisitOptions);
                    final AccumulatorPathVisitor visitor2 = accumulate(dir2, maxDepth, fileVisitOptions);
                    if (visitor1.getDirList().size() != visitor2.getDirList().size()
                            || visitor1.getFileList().size() != visitor2.getFileList().size()) {
                        equals = false;
                    } else {
                        tmpRelativeDirList1 = visitor1.relativizeDirectories(dir1, true, null);
                        tmpRelativeDirList2 = visitor2.relativizeDirectories(dir2, true, null);
                        if (!tmpRelativeDirList1.equals(tmpRelativeDirList2)) {
                            equals = false;
                        } else {
                            tmpRelativeFileList1 = visitor1.relativizeFiles(dir1, true, null);
                            tmpRelativeFileList2 = visitor2.relativizeFiles(dir2, true, null);
                            equals = tmpRelativeFileList1.equals(tmpRelativeFileList2);
                        }
                    }
                }
            }
            relativeFileList1 = tmpRelativeFileList1;
            relativeFileList2 = tmpRelativeFileList2;
        }
    }

    public static final DeleteOption[] EMPTY_DELETE_OPTION_ARRAY = new DeleteOption[0];
    public static final FileVisitOption[] EMPTY_FILE_VISIT_OPTION_ARRAY = new FileVisitOption[0];
    public static final LinkOption[] EMPTY_LINK_OPTION_ARRAY = new LinkOption[0];
    public static final OpenOption[] EMPTY_OPEN_OPTION_ARRAY = new OpenOption[0];
    public static final Path[] EMPTY_PATH_ARRAY = new Path[0];

    private static AccumulatorPathVisitor accumulate(final Path directory, final int maxDepth,
                                                     final FileVisitOption[] fileVisitOptions) throws IOException {
        return visitFileTree(AccumulatorPathVisitor.withLongCounters(), directory,
                toFileVisitOptionSet(fileVisitOptions), maxDepth);
    }


    public static Path copyFile(final URL sourceFile, final Path targetFile, final CopyOption... copyOptions)
            throws IOException {
        try (final InputStream inputStream = sourceFile.openStream()) {
            Files.copy(inputStream, targetFile, copyOptions);
            return targetFile;
        }
    }

    public static Path createParentDirectories(final Path path, final FileAttribute<?>... attrs) throws IOException {
        final Path parent = path.getParent();
        if (parent == null) {
            return null;
        }
        if (Files.isDirectory(parent)) {
            return parent;
        }
        return Files.createDirectories(parent, attrs);
    }

    public static Path current() {
        return Paths.get("");
    }

    public static PathCounters delete(final Path path) throws IOException {
        return delete(path, EMPTY_DELETE_OPTION_ARRAY);
    }

    public static PathCounters delete(final Path path, final DeleteOption... options) throws IOException {
        return Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS) ? deleteDirectory(path, options)
                : deleteFile(path, options);
    }

    public static PathCounters deleteDirectory(final Path directory, final DeleteOption... options) throws IOException {
        return visitFileTree(new DeletingPathVisitor(Counters.longPathCounters(), options), directory)
                .getPathCounters();
    }


    public static PathCounters deleteFile(final Path file, final DeleteOption... options) throws IOException {
        if (Files.isDirectory(file, LinkOption.NOFOLLOW_LINKS)) {
            throw new NoSuchFileException(file.toString());
        }
        final PathCounters pathCounts = Counters.longPathCounters();
        final boolean exists = Files.exists(file, LinkOption.NOFOLLOW_LINKS);
        final long size = exists ? Files.size(file) : 0;
        if (overrideReadOnly(options) && exists) {
            setReadOnly(file, false, LinkOption.NOFOLLOW_LINKS);
        }
        if (Files.deleteIfExists(file)) {
            pathCounts.getFileCounter().increment();
            pathCounts.getByteCounter().add(size);
        }
        return pathCounts;
    }

    public static boolean directoryAndFileContentEquals(final Path path1, final Path path2) throws IOException {
        return directoryAndFileContentEquals(path1, path2, EMPTY_LINK_OPTION_ARRAY, EMPTY_OPEN_OPTION_ARRAY,
                EMPTY_FILE_VISIT_OPTION_ARRAY);
    }

    public static boolean directoryAndFileContentEquals(final Path path1, final Path path2,
                                                        final LinkOption[] linkOptions, final OpenOption[] openOptions, final FileVisitOption[] fileVisitOption)
            throws IOException {
        if (path1 == null && path2 == null) {
            return true;
        }
        if (path1 == null ^ path2 == null) {
            return false;
        }
        if (!Files.exists(path1) && !Files.exists(path2)) {
            return true;
        }
        final RelativeSortedPaths relativeSortedPaths = new RelativeSortedPaths(path1, path2, Integer.MAX_VALUE,
                linkOptions, fileVisitOption);
        if (!relativeSortedPaths.equals) {
            return false;
        }
        final List<Path> fileList1 = relativeSortedPaths.relativeFileList1;
        final List<Path> fileList2 = relativeSortedPaths.relativeFileList2;
        for (final Path path : fileList1) {
            final int binarySearch = Collections.binarySearch(fileList2, path);
            if (binarySearch > -1) {
                if (!fileContentEquals(path1.resolve(path), path2.resolve(path), linkOptions, openOptions)) {
                    return false;
                }
            } else {
                throw new IllegalStateException("Unexpected mismatch.");
            }
        }
        return true;
    }

    public static boolean directoryContentEquals(final Path path1, final Path path2) throws IOException {
        return directoryContentEquals(path1, path2, Integer.MAX_VALUE, EMPTY_LINK_OPTION_ARRAY,
                EMPTY_FILE_VISIT_OPTION_ARRAY);
    }

    public static boolean directoryContentEquals(final Path path1, final Path path2, final int maxDepth,
                                                 final LinkOption[] linkOptions, final FileVisitOption[] fileVisitOptions) throws IOException {
        return new RelativeSortedPaths(path1, path2, maxDepth, linkOptions, fileVisitOptions).equals;
    }


    public static boolean fileContentEquals(final Path path1, final Path path2, final LinkOption[] linkOptions,
                                            final OpenOption[] openOptions) throws IOException {
        if (path1 == null && path2 == null) {
            return true;
        }
        if (path1 == null ^ path2 == null) {
            return false;
        }
        final Path nPath1 = path1.normalize();
        final Path nPath2 = path2.normalize();
        final boolean path1Exists = Files.exists(nPath1, linkOptions);
        if (path1Exists != Files.exists(nPath2, linkOptions)) {
            return false;
        }
        if (!path1Exists) {
            return true;
        }
        if (Files.isDirectory(nPath1, linkOptions)) {
            throw new IOException("Can't compare directories, only files: " + nPath1);
        }
        if (Files.isDirectory(nPath2, linkOptions)) {
            throw new IOException("Can't compare directories, only files: " + nPath2);
        }
        if (Files.size(nPath1) != Files.size(nPath2)) {
            return false;
        }
        if (path1.equals(path2)) {
            return true;
        }
        try (final InputStream inputStream1 = Files.newInputStream(nPath1, openOptions);
             final InputStream inputStream2 = Files.newInputStream(nPath2, openOptions)) {
            return IOUtils.contentEquals(inputStream1, inputStream2);
        }
    }

    public static Path[] filter(final PathFilter filter, final Path... paths) {
        Objects.requireNonNull(filter, "filter");
        if (paths == null) {
            return EMPTY_PATH_ARRAY;
        }
        return filterPaths(filter, Arrays.stream(paths), Collectors.toList()).toArray(EMPTY_PATH_ARRAY);
    }

    private static <R, A> R filterPaths(final PathFilter filter, final Stream<Path> stream,
                                        final Collector<? super Path, A, R> collector) {
        Objects.requireNonNull(filter, "filter");
        Objects.requireNonNull(collector, "collector");
        if (stream == null) {
            return Stream.<Path>empty().collect(collector);
        }
        return stream.filter(p -> {
            try {
                return p != null && filter.accept(p, readBasicFileAttributes(p)) == FileVisitResult.CONTINUE;
            } catch (final IOException e) {
                return false;
            }
        }).collect(collector);
    }

    public static boolean isEmpty(final Path path) throws IOException {
        return Files.isDirectory(path) ? isEmptyDirectory(path) : isEmptyFile(path);
    }

    public static boolean isEmptyDirectory(final Path directory) throws IOException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            if (directoryStream.iterator().hasNext()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmptyFile(final Path file) throws IOException {
        return Files.size(file) <= 0;
    }

    public static boolean isNewer(final Path file, final long timeMillis, final LinkOption... options)
            throws IOException {
        Objects.requireNonNull(file, "file");
        if (!Files.exists(file)) {
            return false;
        }
        return Files.getLastModifiedTime(file, options).toMillis() > timeMillis;
    }

    private static boolean overrideReadOnly(final DeleteOption[] options) {
        if (options == null) {
            return false;
        }
        for (final DeleteOption deleteOption : options) {
            if (deleteOption == StandardDeleteOption.OVERRIDE_READ_ONLY) {
                return true;
            }
        }
        return false;
    }

    public static BasicFileAttributes readBasicFileAttributes(final Path path) throws IOException {
        return Files.readAttributes(path, BasicFileAttributes.class);
    }

    public static BasicFileAttributes readBasicFileAttributesQuietly(final Path path) {
        try {
            return readBasicFileAttributes(path);
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }


    static List<Path> relativize(final Collection<Path> collection, final Path parent, final boolean sort,
                                 final Comparator<? super Path> comparator) {
        Stream<Path> stream = collection.stream().map(parent::relativize);
        if (sort) {
            stream = comparator == null ? stream.sorted() : stream.sorted(comparator);
        }
        return stream.collect(Collectors.toList());
    }

    public static void setReadOnly(final Path path, final boolean readOnly, final LinkOption... options)
            throws IOException {
        final DosFileAttributeView fileAttributeView = Files.getFileAttributeView(path, DosFileAttributeView.class,
                options);
        if (fileAttributeView != null) {
            fileAttributeView.setReadOnly(readOnly);
            return;
        }
        final PosixFileAttributeView posixFileAttributeView = Files.getFileAttributeView(path,
                PosixFileAttributeView.class, options);
        if (posixFileAttributeView != null) {
            final PosixFileAttributes readAttributes = posixFileAttributeView.readAttributes();
            final Set<PosixFilePermission> permissions = readAttributes.permissions();
            permissions.remove(PosixFilePermission.OWNER_WRITE);
            permissions.remove(PosixFilePermission.GROUP_WRITE);
            permissions.remove(PosixFilePermission.OTHERS_WRITE);
            Files.setPosixFilePermissions(path, permissions);
            return;
        }
        throw new IOException("No DosFileAttributeView or PosixFileAttributeView for " + path);
    }

    static Set<FileVisitOption> toFileVisitOptionSet(final FileVisitOption... fileVisitOptions) {
        return fileVisitOptions == null ? EnumSet.noneOf(FileVisitOption.class)
                : Arrays.stream(fileVisitOptions).collect(Collectors.toSet());
    }

    public static <T extends FileVisitor<? super Path>> T visitFileTree(final T visitor, final Path directory)
            throws IOException {
        Files.walkFileTree(directory, visitor);
        return visitor;
    }

    public static <T extends FileVisitor<? super Path>> T visitFileTree(final T visitor, final Path start,
                                                                        final Set<FileVisitOption> options, final int maxDepth) throws IOException {
        Files.walkFileTree(start, options, maxDepth, visitor);
        return visitor;
    }

    public static Stream<Path> walk(final Path start, final PathFilter pathFilter, final int maxDepth,
                                    final boolean readAttributes, final FileVisitOption... options) throws IOException {
        return Files.walk(start, maxDepth, options).filter(path -> pathFilter.accept(path,
                readAttributes ? readBasicFileAttributesQuietly(path) : null) == FileVisitResult.CONTINUE);
    }

    private PathUtils() {
    }

}

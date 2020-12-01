import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utils {
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int EOF = -1;


    private static void checkFileRequirements(final File source, final File destination) throws FileNotFoundException {
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(destination, "target");
        if (!source.exists()) {
            throw new FileNotFoundException("Source '" + source + "' does not exist");
        }
    }

    public static void copyFile(final File srcFile, final File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }

    public static void copyFile(final File srcFile, final File destFile, final boolean preserveFileDate)
            throws IOException {
        copyFile(srcFile, destFile, preserveFileDate, StandardCopyOption.REPLACE_EXISTING);
    }


    public static void copyFile(final File srcFile, final File destFile, final boolean preserveFileDate, final CopyOption... copyOptions)
            throws IOException {
        checkFileRequirements(srcFile, destFile);
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        }
        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        }
        final File parentFile = destFile.getParentFile();
        if (parentFile != null) {
            if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Destination '" + parentFile + "' directory cannot be created");
            }
        }
        if (destFile.exists() && destFile.canWrite() == false) {
            throw new IOException("Destination '" + destFile + "' exists but is read-only");
        }
        doCopyFile(srcFile, destFile, preserveFileDate, copyOptions);
    }

    public static long copyFile(final File input, final OutputStream output) throws IOException {
        try (FileInputStream fis = new FileInputStream(input)) {
            return copyLarge(fis, output);
        }
    }

    public static void copyFileToDirectory(final File sourceFile, final File destinationDir, final boolean preserveFileDate)
            throws IOException {
        Objects.requireNonNull(destinationDir, "destinationDir");
        if (destinationDir.exists() && destinationDir.isDirectory() == false) {
            throw new IllegalArgumentException("Destination '" + destinationDir + "' is not a directory");
        }
        final File destFile = new File(destinationDir, sourceFile.getName());
        copyFile(sourceFile, destFile, preserveFileDate);
    }

    private static void doCopyFile(final File srcFile, final File destFile, final boolean preserveFileDate, final CopyOption... copyOptions)
            throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        final Path srcPath = srcFile.toPath();
        final Path destPath = destFile.toPath();
        Files.copy(srcPath, destPath, copyOptions);

        checkEqualSizes(srcFile, destFile, Files.size(srcPath), Files.size(destPath));
        checkEqualSizes(srcFile, destFile, srcFile.length(), destFile.length());

        if (preserveFileDate) {
            setLastModified(srcFile, destFile);
        }
    }

    private static void setLastModified(final File sourceFile, final File targetFile) throws IOException {
        if (!targetFile.setLastModified(sourceFile.lastModified())) {
            throw new IOException("Failed setLastModified on " + sourceFile);
        }
    }

    private static void checkEqualSizes(final File srcFile, final File destFile, final long srcLen, final long dstLen)
            throws IOException {
        if (srcLen != dstLen) {
            throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile
                    + "' Expected length: " + srcLen + " Actual: " + dstLen);
        }
    }

    public static long copyLarge(final InputStream input, final OutputStream output)
            throws IOException {
        return copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    public static long copy(final InputStream input, final OutputStream output, final int bufferSize)
            throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }

    public static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer)
            throws IOException {
        long count = 0;
        if (input != null) {
            int n;
            while (EOF != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
                count += n;
            }
        }
        return count;
    }

    public static void copyDirectory(final File srcDir, final File destDir) throws IOException {
        copyDirectory(srcDir, destDir, true);
    }

    public static void copyDirectory(final File srcDir, final File destDir,
                                     final boolean preserveFileDate) throws IOException {
        copyDirectory(srcDir, destDir, null, preserveFileDate);
    }

    public static void copyDirectory(final File srcDir, final File destDir, final FileFilter filter,
                                     final boolean preserveFileDate) throws IOException {
        copyDirectory(srcDir, destDir, filter, preserveFileDate, StandardCopyOption.REPLACE_EXISTING);
    }


    public static void copyDirectory(final File srcDir, final File destDir, final FileFilter filter,
                                     final boolean preserveFileDate, final CopyOption... copyOptions) throws IOException {
        checkFileRequirements(srcDir, destDir);
        if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' exists but is not a directory");
        }
        final String srcDirCanonicalPath = srcDir.getCanonicalPath();
        final String destDirCanonicalPath = destDir.getCanonicalPath();
        if (srcDirCanonicalPath.equals(destDirCanonicalPath)) {
            throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
        }

        List<String> exclusionList = null;
        if (destDirCanonicalPath.startsWith(srcDirCanonicalPath)) {
            final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
            if (srcFiles != null && srcFiles.length > 0) {
                exclusionList = new ArrayList<>(srcFiles.length);
                for (final File srcFile : srcFiles) {
                    final File copiedFile = new File(destDir, srcFile.getName());
                    exclusionList.add(copiedFile.getCanonicalPath());
                }
            }
        }
        doCopyDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList, copyOptions);
    }

    private static void doCopyDirectory(final File srcDir, final File destDir, final FileFilter filter,
                                        final boolean preserveFileDate, final List<String> exclusionList, final CopyOption... copyOptions)
            throws IOException {
        final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
        if (srcFiles == null) {  // null if abstract pathname does not denote a directory, or if an I/O error occurs
            throw new IOException("Failed to list contents of " + srcDir);
        }
        if (destDir.exists()) {
            if (destDir.isDirectory() == false) {
                throw new IOException("Destination '" + destDir + "' exists but is not a directory");
            }
        } else {
            if (!destDir.mkdirs() && !destDir.isDirectory()) {
                throw new IOException("Destination '" + destDir + "' directory cannot be created");
            }
        }
        if (destDir.canWrite() == false) {
            throw new IOException("Destination '" + destDir + "' cannot be written to");
        }
        for (final File srcFile : srcFiles) {
            final File dstFile = new File(destDir, srcFile.getName());
            if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
                if (srcFile.isDirectory()) {
                    doCopyDirectory(srcFile, dstFile, filter, preserveFileDate, exclusionList, copyOptions);
                } else {
                    doCopyFile(srcFile, dstFile, preserveFileDate, copyOptions);
                }
            }
        }

        // Do this last, as the above has probably affected directory metadata
        if (preserveFileDate) {
            setLastModified(srcDir, destDir);
        }
    }
}

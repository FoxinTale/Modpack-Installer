package FileUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LineIterator implements Iterator<String>, Closeable {

    private final BufferedReader bufferedReader;
    private String cachedLine;
    private boolean finished = false;

    public LineIterator(final Reader reader) throws IllegalArgumentException {
        if (reader == null) {
            throw new IllegalArgumentException("Reader must not be null");
        }
        if (reader instanceof BufferedReader) {
            bufferedReader = (BufferedReader) reader;
        } else {
            bufferedReader = new BufferedReader(reader);
        }
    }

    @Override
    public boolean hasNext() {
        if (cachedLine != null) {
            return true;
        } else if (finished) {
            return false;
        } else {
            try {
                while (true) {
                    final String line = bufferedReader.readLine();
                    if (line == null) {
                        finished = true;
                        return false;
                    } else if (isValidLine()) {
                        cachedLine = line;
                        return true;
                    }
                }
            } catch (final IOException ioe) {
                IOUtils.closeQuietly(this, ioe::addSuppressed);
                throw new IllegalStateException(ioe);
            }
        }
    }

    protected boolean isValidLine() {
        return true;
    }

    @Override
    public String next() {
        return nextLine();
    }


    public String nextLine() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more lines");
        }
        final String currentLine = cachedLine;
        cachedLine = null;
        return currentLine;
    }

    @Override
    public void close() throws IOException {
        finished = true;
        cachedLine = null;
        IOUtils.close(bufferedReader);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove unsupported on LineIterator");
    }

    @Deprecated
    public static void closeQuietly(final LineIterator iterator) {
        IOUtils.closeQuietly(iterator);
    }

}

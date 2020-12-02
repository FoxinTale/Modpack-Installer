package FileUtils;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayOutputStream extends AbstractByteArrayOutputStream {

    public ByteArrayOutputStream() {
        this(DEFAULT_SIZE);
    }

    public ByteArrayOutputStream(final int size) {
        if (size < 0) {
            throw new IllegalArgumentException(
                    "Negative initial size: " + size);
        }
        synchronized (this) {
            needNewBuffer(size);
        }
    }

    @Override
    public void write(final byte[] b, final int off, final int len) {
        if ((off < 0)
                || (off > b.length)
                || (len < 0)
                || ((off + len) > b.length)
                || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        synchronized (this) {
            writeImpl(b, off, len);
        }
    }

    @Override
    public synchronized void write(final int b) {
        writeImpl(b);
    }

    @Override
    public synchronized int write(final InputStream in) throws IOException {
        return writeImpl(in);
    }

    @Override
    public synchronized int size() {
        return count;
    }

    @Override
    public synchronized void reset() {
        resetImpl();
    }

    @Override
    public synchronized byte[] toByteArray() {
        return toByteArrayImpl();
    }
}

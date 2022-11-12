package FileUtils;

import java.io.Serializable;
import java.io.Writer;

public class StringBuilderWriter extends Writer implements Serializable {

    private static final long serialVersionUID = -146927496096066153L;
    private final StringBuilder builder;

    public StringBuilderWriter() {
        this.builder = new StringBuilder();
    }

    @Override
    public Writer append(final char value) {
        builder.append(value);
        return this;
    }

    @Override
    public Writer append(final CharSequence value) {
        builder.append(value);
        return this;
    }

    @Override
    public Writer append(final CharSequence value, final int start, final int end) {
        builder.append(value, start, end);
        return this;
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() {
        // no-op
    }

    @Override
    public void write(final String value) {
        if (value != null) {
            builder.append(value);
        }
    }

    @Override
    public void write(final char[] value, final int offset, final int length) {
        if (value != null) {
            builder.append(value, offset, length);
        }
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}

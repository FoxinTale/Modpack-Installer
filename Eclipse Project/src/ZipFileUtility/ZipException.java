package ZipFileUtility;

import java.io.IOException;

public class ZipException extends IOException {

    private static final long serialVersionUID = 1L;

    private Type type = Type.UNKNOWN;

    public ZipException(String message) {
        super(message);
    }

    public ZipException(Exception rootException) {
        super(rootException);
    }

    public ZipException(String message, Exception rootException) {
        super(message, rootException);
    }

    public ZipException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public ZipException(String message, Throwable throwable, Type type) {
        super(message, throwable);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        TASK_CANCELLED_EXCEPTION,
        UNKNOWN_COMPRESSION_METHOD,
        FILE_NOT_FOUND,
        UNKNOWN
    }
}

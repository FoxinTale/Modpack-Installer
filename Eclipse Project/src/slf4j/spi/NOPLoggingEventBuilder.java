package slf4j.spi;

import java.util.function.Supplier;

/**
 * <p>A no-operation implementation of {@link LoggingEventBuilder}.</p>
 *
 * <p>As the name indicates, the method in this class do nothing, except when a return value is expected
 * in which case a singleton, i.e. the unique instance of this class is returned.
 * </p
 *
 * @author Ceki G&uuml;lc&uuml;
 * @since 2.0.0
 *
 */
public class NOPLoggingEventBuilder implements LoggingEventBuilder {

    static final NOPLoggingEventBuilder SINGLETON = new NOPLoggingEventBuilder();

    private NOPLoggingEventBuilder() {
    }
    public static LoggingEventBuilder singleton() {
        return SINGLETON;
    }

    @Override
    public void log() {
    }

    @Override
    public LoggingEventBuilder setMessage(String message) {
        return this;
    }
    @Override
    public LoggingEventBuilder setMessage(Supplier<String> messageSupplier) {
        return this;
    }

    @Override
    public void log(String message) {
    }

    @Override
    public void log(Supplier<String> messageSupplier) {
    }

    @Override
    public void log(String message, Object arg) {
    }

    @Override
    public void log(String message, Object arg0, Object arg1) {
    }

    @Override
    public void log(String message, Object... args) {

    }

}
package slf4j.event;

import slf4j.Logger;
import slf4j.Marker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A default implementation of {@link LoggingEvent}.
 * 
 * @author Ceki G&uuml;lc&uuml;
 *
 * @since 2.0.0
 */
public class DefaultLoggingEvent implements LoggingEvent {

    Logger logger;
    Level level;

    String message;
    List<Marker> markers;
    List<Object> arguments;
    List<KeyValuePair> keyValuePairs;

    Throwable throwable;

    String callerBoundary;

    public DefaultLoggingEvent(Level level, Logger logger) {
        this.logger = logger;
        this.level = level;
    }

    @Override
    public List<Marker> getMarkers() {
        return markers;
    }

    public void addArgument(Object p) {
        getNonNullArguments().add(p);
    }

    public void addArguments(Object... args) {
        getNonNullArguments().addAll(Arrays.asList(args));
    }

    private List<Object> getNonNullArguments() {
        if (arguments == null) {
            arguments = new ArrayList<>(3);
        }
        return arguments;
    }

    @Override
    public List<Object> getArguments() {
        return arguments;
    }

    @Override
    public Object[] getArgumentArray() {
        if (arguments == null)
            return null;
        return arguments.toArray();
    }

    @Override
    public List<KeyValuePair> getKeyValuePairs() {
        return keyValuePairs;
    }

    public void setThrowable(Throwable cause) {
        this.throwable = cause;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Throwable getThrowable() {
        return throwable;
    }

    public void setCallerBoundary(String fqcn) {
        this.callerBoundary = fqcn;
    }

}
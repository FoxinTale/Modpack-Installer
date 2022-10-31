package slf4j.spi;

import slf4j.Logger;
import slf4j.Marker;

/**
 * An <b>optional</b> interface helping integration with logging systems capable of 
 * extracting location information. This interface is mainly used by SLF4J bridges 
 * such as jcl-over-slf4j, jul-to-slf4j and log4j-over-slf4j or {@link Logger} wrappers
 * which need to provide hints so that the underlying logging system can extract
 * the correct location information (method name, line number).
 *
 * @author Ceki G&uuml;lc&uuml;
 * @since 1.3
 */
public interface LocationAwareLogger extends Logger {

    // these constants should be in EventConstants. However, in order to preserve binary backward compatibility
    // we keep these constants here. {@link EventConstants} redefines these constants using the values  below.
    int TRACE_INT = 0;
    int DEBUG_INT = 10;
    int INFO_INT = 20;
    int WARN_INT = 30;
    int ERROR_INT = 40;

    /**
     * Printing method with support for location information. 
     * 
     * @param marker The marker to be used for this event, may be null.
     * 
     * @param fqcn The fully qualified class name of the <b>logger instance</b>,
     * typically the logger class, logger bridge or a logger wrapper.
     * 
     * @param level One of the level integers defined in this interface
     * 
     * @param message The message for the log event
     * @param t Throwable associated with the log event, may be null.
     */
    void log(Marker marker, String fqcn, int level, String message, Object[] argArray, Throwable t);

}

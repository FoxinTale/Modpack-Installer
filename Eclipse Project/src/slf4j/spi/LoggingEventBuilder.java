
package slf4j.spi;

import slf4j.helpers.CheckReturnValue;

import java.util.function.Supplier;


public interface LoggingEventBuilder {


    /**
     *  Sets the message of the logging event.
     *
     *  @since 2.0.0-beta0
     */
    @CheckReturnValue
    LoggingEventBuilder setMessage(String message);

    /**
     * Sets the message of the event via a message supplier.
     *
     * @param messageSupplier supplies a String to be used as the message for the event
     * @since 2.0.0-beta0
     */
    @CheckReturnValue
    LoggingEventBuilder setMessage(Supplier<String> messageSupplier);


    void log();

    /**
     * Equivalent to calling {@link #setMessage(String)} followed by {@link #log()};
     *
     * @param message the message to log
     */
    void log(String message);

    void log(String message, Object arg);
    void log(String message, Object arg0, Object arg1);

    void log(String message, Object... args);
    void log(Supplier<String> messageSupplier);

}

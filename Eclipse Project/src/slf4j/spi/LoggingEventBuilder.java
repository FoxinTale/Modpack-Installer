
package slf4j.spi;

import slf4j.Marker;
import slf4j.helpers.CheckReturnValue;

import java.util.function.Supplier;


public interface LoggingEventBuilder {

    /**
     * Set the cause for the logging event being built.
     * @param cause a throwable
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @CheckReturnValue
    LoggingEventBuilder setCause(Throwable cause);

    /**
     * A {@link Marker marker} to the event being built.
     *
     * @param marker a Marker instance to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @CheckReturnValue
    LoggingEventBuilder addMarker(Marker marker);

    /**
     * Add an argument to the event being built.
     *
     * @param p an Object to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @CheckReturnValue
    LoggingEventBuilder addArgument(Object p);

    /**
     * Add an argument supplier to the event being built.
     *
     * @param objectSupplier an Object supplier to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @CheckReturnValue
    LoggingEventBuilder addArgument(Supplier<?> objectSupplier);


    /**
     * Add a {@link slf4j.event.KeyValuePair key value pair} to the event being built.
     *
     * @param key the key of the key value pair.
     * @param value the value of the key value pair.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @CheckReturnValue
    LoggingEventBuilder addKeyValue(String key, Object value);

    /**
     * Add a {@link slf4j.event.KeyValuePair key value pair} to the event being built.
     *
     * @param key the key of the key value pair.
     * @param valueSupplier a supplier of a value for the key value pair.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @CheckReturnValue
    LoggingEventBuilder addKeyValue(String key, Supplier<Object> valueSupplier);

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

    /**
     * After the logging event is built, performs actual logging. This method must be called
     * for logging to occur.
     *
     * If the call to {@link #log()}  is omitted, a {@link slf4j.event.LoggingEvent LoggingEvent}
     * will be built but no logging will occur.
     *
     * @since 2.0.0-beta0
     */
    void log();

    /**
     * Equivalent to calling {@link #setMessage(String)} followed by {@link #log()};
     *
     * @param message the message to log
     */
    void log(String message);

    /**
     * Equivalent to calling {@link #setMessage(String)} followed by {@link #addArgument(Object)}}
     * and then {@link #log()}
     *
     * @param message the message to log
     * @param arg an argument to be used with the message to log
     */
    void log(String message, Object arg);

    /**
     * Equivalent to calling {@link #setMessage(String)} followed by two calls to
     * {@link #addArgument(Object)} and then {@link #log()}
     *
     * @param message the message to log
     * @param arg0 first argument to be used with the message to log
     * @param arg1 second argument to be used with the message to log
     */
    void log(String message, Object arg0, Object arg1);


    /**
     * Equivalent to calling {@link #setMessage(String)} followed by zero or more calls to
     * {@link #addArgument(Object)} (depending on the size of args array) and then {@link #log()}
     *
     * @param message the message to log
     * @param args a list (actually an array) of arguments to be used with the message to log
     */
    void log(String message, Object... args);

    /**
     * Equivalent to calling {@link #setMessage(Supplier)} followed by {@link #log()}
     *
     * @param messageSupplier a Supplier returning a message of type String
     */
    void log(Supplier<String> messageSupplier);

}

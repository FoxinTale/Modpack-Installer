package slf4j.spi;

import slf4j.ILoggerFactory;

/**
 * An internal interface which helps the static {@link slf4j.LoggerFactory}
 * class bind with the appropriate {@link ILoggerFactory} instance. 
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @deprecated
 */
public interface LoggerFactoryBinder {

    /**
     * Return the instance of {@link ILoggerFactory} that 
     * {@link slf4j.LoggerFactory} class should bind to.
     * 
     * @return the instance of {@link ILoggerFactory} that 
     * {@link slf4j.LoggerFactory} class should bind to.
     */
    public ILoggerFactory getLoggerFactory();

    /**
     * The String form of the {@link ILoggerFactory} object that this 
     * <code>LoggerFactoryBinder</code> instance is <em>intended</em> to return. 
     * 
     * <p>This method allows the developer to interrogate this binder's intention
     * which may be different from the {@link ILoggerFactory} instance it is able to 
     * yield in practice. The discrepancy should only occur in case of errors.
     * 
     * @return the class name of the intended {@link ILoggerFactory} instance
     */
    public String getLoggerFactoryClassStr();
}

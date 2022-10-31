package slf4j.spi;

import slf4j.IMarkerFactory;

/**
 * An internal interface which helps the static {@link slf4j.MarkerFactory}
 * class bind with the appropriate {@link IMarkerFactory} instance. 
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @deprecated
 */
public interface MarkerFactoryBinder {

    /**
     * Return the instance of {@link IMarkerFactory} that 
     * {@link slf4j.MarkerFactory} class should bind to.
     * 
     * @return the instance of {@link IMarkerFactory} that 
     * {@link slf4j.MarkerFactory} class should bind to.
     */
    public IMarkerFactory getMarkerFactory();

    /**
     * The String form of the {@link IMarkerFactory} object that this 
     * <code>MarkerFactoryBinder</code> instance is <em>intended</em> to return. 
     * 
     * <p>This method allows the developer to interrogate this binder's intention
     * which may be different from the {@link IMarkerFactory} instance it is able to 
     * return. Such a discrepancy should only occur in case of errors.
     * 
     * @return the class name of the intended {@link IMarkerFactory} instance
     */
    public String getMarkerFactoryClassStr();
}

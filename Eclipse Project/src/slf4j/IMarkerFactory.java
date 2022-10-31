package slf4j;

/**
 * Implementations of this interface are used to manufacture {@link Marker}
 * instances.
 * 
 * <p>See the section <a href="http://slf4j.org/faq.html#3">Implementing 
 * the SLF4J API</a> in the FAQ for details on how to make your logging 
 * system conform to SLF4J.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public interface IMarkerFactory {

    /**
     * Checks if the marker with the name already exists. If name is null, then false 
     * is returned.
     *  
     * @param name logger name to check for
     * @return true id the marker exists, false otherwise. 
     */
    boolean exists(String name);

}

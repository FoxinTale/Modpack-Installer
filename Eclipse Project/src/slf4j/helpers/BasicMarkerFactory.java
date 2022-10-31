package slf4j.helpers;

import slf4j.IMarkerFactory;
import slf4j.Marker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * An almost trivial implementation of the {@link IMarkerFactory}
 * interface which creates {@link BasicMarker} instances.
 *
 * <p>Simple logging systems can conform to the SLF4J API by binding
 * with an instance of this class.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public class BasicMarkerFactory implements IMarkerFactory {

    private final ConcurrentMap<String, Marker> markerMap = new ConcurrentHashMap<>();

    /**
     * Regular users should <em>not</em> create
     * <code>BasicMarkerFactory</code> instances. <code>Marker</code>
     * instances can be obtained using the static method.
     */
    public BasicMarkerFactory() {
    }

    /**
     * Manufacture a {@link BasicMarker} instance by name. If the instance has been 
     * created earlier, return the previously created instance. 
     * 
     * @param name the name of the marker to be created
     * @return a Marker instance
     */
    public Marker getMarker(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Marker name cannot be null");
        }

        Marker marker = markerMap.get(name);
        if (marker == null) {
            marker = new BasicMarker(name);
            Marker oldMarker = markerMap.putIfAbsent(name, marker);
            if (oldMarker != null) {
                marker = oldMarker;
            }
        }
        return marker;
    }

    /**
     * Does the name marked already exist?
     */
    public boolean exists(String name) {
        if (name == null) {
            return false;
        }
        return markerMap.containsKey(name);
    }

    public boolean detachMarker(String name) {
        if (name == null) {
            return false;
        }
        return (markerMap.remove(name) != null);
    }

    public Marker getDetachedMarker(String name) {
        return new BasicMarker(name);
    }

}

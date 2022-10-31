package slf4j.helpers;

import slf4j.spi.MDCAdapter;

import java.util.Deque;
import java.util.Map;

/**
 * This adapter is an empty implementation of the {@link MDCAdapter} interface.
 * It is used for all logging systems which do not support mapped
 * diagnostic contexts such as JDK14, simple and NOP. 
 * 
 * @author Ceki G&uuml;lc&uuml;
 * 
 * @since 1.4.1
 */
public class NOPMDCAdapter implements MDCAdapter {

    public void clear() {
    }

    public String get(String key) {
        return null;
    }

    public void put(String key, String val) {
    }

    public void remove(String key) {
    }

    public Map<String, String> getCopyOfContextMap() {
        return null;
    }

    public void setContextMap(Map<String, String> contextMap) {
        // NOP
    }

    @Override
    public void pushByKey(String key, String value) {
    }

    @Override
    public String popByKey(String key) {
        return null;
    }

    @Override
    public Deque<String> getCopyOfDequeByKey(String key) {
        return null;
    }
    
    public void clearDequeByKey(String key) {
    }

}

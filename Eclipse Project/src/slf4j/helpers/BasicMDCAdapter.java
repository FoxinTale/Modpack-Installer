package slf4j.helpers;

import slf4j.spi.MDCAdapter;

import java.util.*;

/**
 * Basic MDC implementation, which can be used with logging systems that lack
 * out-of-the-box MDC support.
 *
 * This code was initially inspired by  logback's LogbackMDCAdapter. However,
 * LogbackMDCAdapter has evolved and is now considerably more sophisticated.
 *
 * @author Ceki Gulcu
 * @author Maarten Bosteels
 * @author Lukasz Cwik
 * 
 * @since 1.5.0
 */
public class BasicMDCAdapter implements MDCAdapter {

    private final InheritableThreadLocal<Map<String, String>> inheritableThreadLocalMap = new InheritableThreadLocal<Map<String, String>>() {
        @Override
        protected Map<String, String> childValue(Map<String, String> parentValue) {
            if (parentValue == null) {
                return null;
            }
            return new HashMap<>(parentValue);
        }
    };

    /**
     * Put a context value (the <code>val</code> parameter) as identified with
     * the <code>key</code> parameter into the current thread's context map.
     * Note that contrary to log4j, the <code>val</code> parameter can be null.
     *
     * <p>
     * If the current thread does not have a context map it is created as a side
     * effect of this call.
     *
     * @throws IllegalArgumentException
     *                 in case the "key" parameter is null
     */
    public void put(String key, String val) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        Map<String, String> map = inheritableThreadLocalMap.get();
        if (map == null) {
            map = new HashMap<>();
            inheritableThreadLocalMap.set(map);
        }
        map.put(key, val);
    }

    /**
     * Get the context identified by the <code>key</code> parameter.
     */
    public String get(String key) {
        Map<String, String> map = inheritableThreadLocalMap.get();
        if ((map != null) && (key != null)) {
            return map.get(key);
        } else {
            return null;
        }
    }

    /**
     * Remove the context identified by the <code>key</code> parameter.
     */
    public void remove(String key) {
        Map<String, String> map = inheritableThreadLocalMap.get();
        if (map != null) {
            map.remove(key);
        }
    }

    /**
     * Clear all entries in the MDC.
     */
    public void clear() {
        Map<String, String> map = inheritableThreadLocalMap.get();
        if (map != null) {
            map.clear();
            inheritableThreadLocalMap.remove();
        }
    }

}

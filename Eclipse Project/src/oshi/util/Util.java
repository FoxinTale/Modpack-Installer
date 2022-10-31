package oshi.util;

import slf4j.Logger;
import slf4j.LoggerFactory;

/**
 * General utility methods
 *
 * @author widdis[at]gmail[dot]com
 */
public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    private Util() {
    }

    /**
     * Sleeps for the specified number of milliseconds.
     *
     * @param ms
     *            How long to sleep
     */
    public static void sleep(long ms) {
        try {
            LOG.trace("Sleeping for {} ms", ms);
            Thread.sleep(ms);
        } catch (InterruptedException e) { // NOSONAR squid:S2142
            LOG.warn("Interrupted while sleeping for {} ms: {}", ms, e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Tests if a String matches another String with a wildcard pattern.
     * 
     * @param text
     *            The String to test
     * @param pattern
     *            The String containing a wildcard pattern where ? represents a
     *            single character and * represents any number of characters. If
     *            the first character of the pattern is a carat (^) the test is
     *            performed against the remaining characters and the result of
     *            the test is the opposite.
     * @return True if the String matches or if the first character is ^ and the
     *         remainder of the String does not match.
     */
    public static boolean wildcardMatch(String text, String pattern) {
        if (pattern.length() > 0 && pattern.charAt(0) == '^') {
            return !wildcardMatch(text, pattern.substring(1));
        }
        return text.matches(pattern.replace("?", ".?").replace("*", ".*?"));
    }
}

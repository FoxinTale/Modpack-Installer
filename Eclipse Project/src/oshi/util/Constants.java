package oshi.util;

/**
 * General constants used in multiple classes
 */
public class Constants {

    /**
     * String to report for unknown information
     */
    public static final String UNKNOWN = "unknown";

    /**
     * Note: /sys/class/dmi/id symlinks here, but /sys/devices/ is the
     * official/approved path for sysfs information
     */
    public static final String SYSFS_SERIAL_PATH = "/sys/devices/virtual/dmi/id/";

    /**
     * Everything in this class is static, never instantiate it
     */
    private Constants() {
        throw new AssertionError();
    }
}

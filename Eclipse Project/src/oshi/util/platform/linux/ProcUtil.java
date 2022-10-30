package oshi.util.platform.linux;

import oshi.util.FileUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * Provides access to some /proc filesystem info on Linux
 *
 * @author widdis[at]gmail[dot]com
 */
public class ProcUtil {
    private static final Pattern DIGITS = Pattern.compile("\\d+"); // NOSONAR-squid:S1068

    private ProcUtil() {
    }

    /**
     * Parses the first value in /proc/uptime for seconds since boot
     *
     * @return Seconds since boot
     */
    public static double getSystemUptimeSeconds() {
        String uptime = FileUtil.getStringFromFile("/proc/uptime");
        int spaceIndex = uptime.indexOf(' ');
        try {
            if (spaceIndex < 0) {
                // No space, error
                return 0d;
            } else {
                return Double.parseDouble(uptime.substring(0, spaceIndex));
            }
        } catch (NumberFormatException nfe) {
            return 0d;
        }
    }

    /**
     * Gets an array of files in the /proc directory with only numeric digit
     * filenames, corresponding to processes
     *
     * @return An array of File objects for the process files
     */
    public static File[] getPidFiles() {
        File procdir = new File("/proc");
        File[] pids = procdir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return DIGITS.matcher(file.getName()).matches();
            }
        });
        return pids != null ? pids : new File[0];
    }
}
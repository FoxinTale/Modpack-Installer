package oshi.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads from lsof into a map
 *
 * @author widdis[at]gmail[dot]com
 */
public class LsofUtil {

    private LsofUtil() {
    }

    public static Map<Integer, String> getCwdMap(int pid) {
        List<String> lsof = ExecutingCommand.runNative("lsof -Fn -d cwd" + (pid < 0 ? "" : " -p " + pid));
        Map<Integer, String> cwdMap = new HashMap<>();
        Integer key = -1;
        for (String line : lsof) {
            if (line.isEmpty()) {
                continue;
            }
            switch (line.charAt(0)) {
            case 'p':
                key = ParseUtil.parseIntOrDefault(line.substring(1), -1);
                break;
            case 'n':
                cwdMap.put(key, line.substring(1));
                break;
            case 'f':
                // ignore the 'cwd' file descriptor
            default:
                break;
            }
        }
        return cwdMap;
    }
}

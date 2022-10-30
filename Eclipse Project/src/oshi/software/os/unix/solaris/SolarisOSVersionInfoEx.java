package oshi.software.os.unix.solaris;

import oshi.software.common.AbstractOSVersionInfoEx;
import oshi.util.ExecutingCommand;
import oshi.util.ParseUtil;

public class SolarisOSVersionInfoEx extends AbstractOSVersionInfoEx {

    private static final long serialVersionUID = 1L;

    public SolarisOSVersionInfoEx() {
        // TODO use sysinfo() instead of commandline
        String versionInfo = ExecutingCommand.getFirstAnswer("uname -rv");
        String[] split = ParseUtil.whitespaces.split(versionInfo);
        setVersion(split[0]);
        if (split.length > 1) {
            setBuildNumber(split[1]);
        }
        setCodeName("Solaris");
    }
}

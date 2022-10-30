package oshi.software.os.unix.freebsd;

import oshi.software.common.AbstractOSVersionInfoEx;
import oshi.util.platform.unix.freebsd.BsdSysctlUtil;

public class FreeBsdOSVersionInfoEx extends AbstractOSVersionInfoEx {

    private static final long serialVersionUID = 1L;

    public FreeBsdOSVersionInfoEx() {
        setVersion(BsdSysctlUtil.sysctl("kern.osrelease", ""));
        String versionInfo = BsdSysctlUtil.sysctl("kern.version", "");
        String osType = BsdSysctlUtil.sysctl("kern.ostype", "FreeBSD");
        setBuildNumber(versionInfo.split(":")[0].replace(osType, "").replace(getVersion(), "").trim());
        setCodeName("");
    }
}

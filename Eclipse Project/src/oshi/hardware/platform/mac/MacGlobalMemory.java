package oshi.hardware.platform.mac;

import oshi.hardware.common.AbstractGlobalMemory;
import oshi.util.platform.mac.SysctlUtil;

/**
 * Memory obtained by host_statistics (vm_stat) and sysctl.
 */
public class MacGlobalMemory extends AbstractGlobalMemory {

    private static final long serialVersionUID = 1L;
    @Override
    public long getTotal() {
        if (this.memTotal < 0) {
            long memory = SysctlUtil.sysctl("hw.memsize", -1L);
            if (memory >= 0) {
                this.memTotal = memory;
            }
        }
        return this.memTotal;
    }

}

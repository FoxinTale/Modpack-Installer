package oshi.hardware.platform.unix.freebsd;

import oshi.hardware.common.AbstractGlobalMemory;
import oshi.util.platform.unix.freebsd.BsdSysctlUtil;

/**
 * Memory obtained by sysctl vm.stats
 */
public class FreeBsdGlobalMemory extends AbstractGlobalMemory {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTotal() {
        if (this.memTotal < 0) {
            this.memTotal = BsdSysctlUtil.sysctl("hw.physmem", 0L);
        }
        return this.memTotal;
    }
}

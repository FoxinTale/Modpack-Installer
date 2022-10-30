package oshi.hardware.platform.mac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.Native; // NOSONAR
import com.sun.jna.platform.mac.SystemB;
import com.sun.jna.platform.mac.SystemB.VMStatistics;
import com.sun.jna.platform.mac.SystemB.XswUsage;
import com.sun.jna.ptr.IntByReference;

import oshi.hardware.common.AbstractVirtualMemory;
import oshi.util.ParseUtil;
import oshi.util.platform.mac.SysctlUtil;

/**
 * Memory obtained by host_statistics (vm_stat) and sysctl.
 */
public class MacVirtualMemory extends AbstractVirtualMemory {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(MacVirtualMemory.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public long getSwapUsed() {
        if (this.swapUsed < 0) {
            updateSwapUsed();
        }
        return this.swapUsed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getSwapTotal() {
        if (this.swapTotal < 0) {
            updateSwapUsed();
        }
        return this.swapTotal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getSwapPagesIn() {
        if (this.swapPagesIn < 0) {
            updateSwapInOut();
        }
        return this.swapPagesIn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getSwapPagesOut() {
        if (this.swapPagesOut < 0) {
            updateSwapInOut();
        }
        return this.swapPagesOut;
    }

    private void updateSwapUsed() {
        XswUsage xswUsage = new XswUsage();
        if (!SysctlUtil.sysctl("vm.swapusage", xswUsage)) {
            return;
        }
        this.swapUsed = xswUsage.xsu_used;
        this.swapTotal = xswUsage.xsu_total;
    }

    private void updateSwapInOut() {
        VMStatistics vmStats = new VMStatistics();
        if (0 != SystemB.INSTANCE.host_statistics(SystemB.INSTANCE.mach_host_self(), SystemB.HOST_VM_INFO, vmStats,
                new IntByReference(vmStats.size() / SystemB.INT_SIZE))) {
            LOG.error("Failed to get host VM info. Error code: {}", Native.getLastError());
            return;
        }
        this.swapPagesIn = ParseUtil.unsignedIntToLong(vmStats.pageins);
        this.swapPagesOut = ParseUtil.unsignedIntToLong(vmStats.pageouts);
    }
}

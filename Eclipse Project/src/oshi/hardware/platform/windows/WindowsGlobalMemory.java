package oshi.hardware.platform.windows;

import slf4j.Logger;
import slf4j.LoggerFactory;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Psapi;
import com.sun.jna.platform.win32.Psapi.PERFORMANCE_INFORMATION;

import oshi.hardware.common.AbstractGlobalMemory;

/**
 * Memory obtained by Performance Info.
 */
public class WindowsGlobalMemory extends AbstractGlobalMemory {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(WindowsGlobalMemory.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTotal() {
        if (this.memTotal < 0) {
            updatePerfInfo();
        }
        return this.memTotal;
    }


    private void updatePerfInfo() {
        PERFORMANCE_INFORMATION perfInfo = new PERFORMANCE_INFORMATION();
        if (!Psapi.INSTANCE.GetPerformanceInfo(perfInfo, perfInfo.size())) {
            LOG.error("Failed to get Performance Info. Error code: {}", Kernel32.INSTANCE.GetLastError());
            return;
        }
        this.pageSize = perfInfo.PageSize.longValue();
        this.memAvailable = this.pageSize * perfInfo.PhysicalAvailable.longValue();
        this.memTotal = this.pageSize * perfInfo.PhysicalTotal.longValue();
    }
}

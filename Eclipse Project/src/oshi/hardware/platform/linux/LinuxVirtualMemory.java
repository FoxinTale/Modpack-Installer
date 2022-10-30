package oshi.hardware.platform.linux;

import oshi.hardware.common.AbstractVirtualMemory;
import oshi.util.FileUtil;
import oshi.util.ParseUtil;

import java.util.List;

/**
 * Memory obtained by /proc/meminfo and /proc/vmstat
 */
public class LinuxVirtualMemory extends AbstractVirtualMemory {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public long getSwapUsed() {
        if (this.swapUsed < 0) {
            updateMemInfo();
        }
        return this.swapUsed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getSwapTotal() {
        if (this.swapTotal < 0) {
            updateMemInfo();
        }
        return this.swapTotal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getSwapPagesIn() {
        if (this.swapPagesIn < 0) {
            updateVmStat();
        }
        return this.swapPagesIn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getSwapPagesOut() {
        if (this.swapPagesOut < 0) {
            updateVmStat();
        }
        return this.swapPagesOut;
    }

    private void updateMemInfo() {
        long swapFree = 0;

        List<String> memInfo = FileUtil.readFile("/proc/meminfo");
        for (String checkLine : memInfo) {
            String[] memorySplit = ParseUtil.whitespaces.split(checkLine);
            if (memorySplit.length > 1) {
                switch (memorySplit[0]) {
                case "SwapTotal:":
                    this.swapTotal = parseMeminfo(memorySplit);
                    break;
                case "SwapFree:":
                    swapFree = parseMeminfo(memorySplit);
                    break;
                default:
                    // do nothing with other lines
                    break;
                }
            }
        }
        this.swapUsed = this.swapTotal - swapFree;
    }

    private void updateVmStat() {
        List<String> vmStat = FileUtil.readFile("/proc/vmstat");
        for (String checkLine : vmStat) {
            String[] memorySplit = ParseUtil.whitespaces.split(checkLine);
            if (memorySplit.length > 1) {
                switch (memorySplit[0]) {
                case "pgpgin":
                    this.swapPagesIn = ParseUtil.parseLongOrDefault(memorySplit[1], 0L);
                    break;
                case "pgpgout":
                    this.swapPagesOut = ParseUtil.parseLongOrDefault(memorySplit[1], 0L);
                    break;
                default:
                    // do nothing with other lines
                    break;
                }
            }
        }
    }

    /**
     * Parses lines from the display of /proc/meminfo
     *
     * @param memorySplit
     *            Array of Strings representing the 3 columns of /proc/meminfo
     * @return value, multiplied by 1024 if kB is specified
     */
    private long parseMeminfo(String[] memorySplit) {
        if (memorySplit.length < 2) {
            return 0l;
        }
        long memory = ParseUtil.parseLongOrDefault(memorySplit[1], 0L);
        if (memorySplit.length > 2 && "kB".equals(memorySplit[2])) {
            memory *= 1024;
        }
        return memory;
    }
}

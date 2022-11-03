package oshi.hardware.platform.linux;

import com.sun.jna.Native;
import com.sun.jna.platform.linux.LibC;
import com.sun.jna.platform.linux.LibC.Sysinfo;
import slf4j.Logger;
import slf4j.LoggerFactory;
import oshi.hardware.common.AbstractGlobalMemory;
import oshi.util.ExecutingCommand;
import oshi.util.FileUtil;
import oshi.util.ParseUtil;

import java.util.List;

/**
 * Memory obtained by /proc/meminfo and sysinfo.totalram
 */
public class LinuxGlobalMemory extends AbstractGlobalMemory {
    private static final Logger LOG = LoggerFactory.getLogger(LinuxGlobalMemory.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTotal() {
        if (this.memTotal < 0) {
            readSysinfo();
        }
        return this.memTotal;
    }


    private void readSysinfo() {
        try {
            Sysinfo info = new Sysinfo();
            if (0 == LibC.INSTANCE.sysinfo(info)) {
                this.memTotal = info.totalram.longValue();
                this.pageSize = info.mem_unit;
            } else {
                LOG.error("Failed to get sysinfo. Error code: {}", Native.getLastError());
            }
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            LOG.error("Failed to get sysinfo. {}", e);
            this.pageSize = ParseUtil.parseLongOrDefault(ExecutingCommand.getFirstAnswer("getconf PAGE_SIZE"), 4096L);
            updateMemInfo();
        }
    }

    /**
     * Updates instance variables from reading /proc/meminfo. While most of the
     * information is available in the sysinfo structure, the most accurate
     * calculation of MemAvailable is only available from reading this
     * pseudo-file. The maintainers of the Linux Kernel have indicated this
     * location will be kept up to date if the calculation changes: see
     * https://git.kernel.org/cgit/linux/kernel/git/torvalds/linux.git/commit/?
     * id=34e431b0ae398fc54ea69ff85ec700722c9da773
     *
     * Internally, reading /proc/meminfo is faster than sysinfo because it only
     * spends time populating the memory components of the sysinfo structure.
     */
    private void updateMemInfo() {
        long memFree = 0;
        long activeFile = 0;
        long inactiveFile = 0;
        long sReclaimable = 0;

        List<String> memInfo = FileUtil.readFile("/proc/meminfo");
        for (String checkLine : memInfo) {
            String[] memorySplit = ParseUtil.whitespaces.split(checkLine);
            if (memorySplit.length > 1) {
                switch (memorySplit[0]) {
                case "MemTotal:":
                    this.memTotal = parseMeminfo(memorySplit);
                    break;
                case "MemAvailable:":
                    this.memAvailable = parseMeminfo(memorySplit);
                    // We're done!
                    return;
                case "MemFree:":
                    memFree = parseMeminfo(memorySplit);
                    break;
                case "Active(file):":
                    activeFile = parseMeminfo(memorySplit);
                    break;
                case "Inactive(file):":
                    inactiveFile = parseMeminfo(memorySplit);
                    break;
                case "SReclaimable:":
                    sReclaimable = parseMeminfo(memorySplit);
                    break;
                default:
                    // do nothing with other lines
                    break;
                }
            }
        }
        // We didn't find MemAvailable so we estimate from other fields
        this.memAvailable = memFree + activeFile + inactiveFile + sReclaimable;
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
package oshi.hardware.platform.windows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.Memory; // NOSONAR

import oshi.hardware.PowerSource;
import oshi.hardware.common.AbstractPowerSource;
import oshi.jna.platform.windows.PowrProf;
import oshi.jna.platform.windows.PowrProf.POWER_INFORMATION_LEVEL;
import oshi.jna.platform.windows.PowrProf.SystemBatteryState;
import oshi.util.FormatUtil;

/**
 * A Power Source
 *
 * @author widdis[at]gmail[dot]com
 */
public class WindowsPowerSource extends AbstractPowerSource {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(WindowsPowerSource.class);

    public WindowsPowerSource(String newName, double newRemainingCapacity, double newTimeRemaining) {
        super(newName, newRemainingCapacity, newTimeRemaining);
        LOG.debug("Initialized WindowsPowerSource");
    }

    /**
     * Gets Battery Information.
     *
     * @return An array of PowerSource objects representing batteries, etc.
     */
    public static PowerSource[] getPowerSources() {
        // Windows provides a single unnamed battery
        String name = "System Battery";
        WindowsPowerSource[] psArray = new WindowsPowerSource[1];
        // Get structure
        int size = new SystemBatteryState().size();
        Memory mem = new Memory(size);
        if (0 == PowrProf.INSTANCE.CallNtPowerInformation(POWER_INFORMATION_LEVEL.SYSTEM_BATTERY_STATE, null, 0, mem,
                size)) {
            SystemBatteryState batteryState = new SystemBatteryState(mem);
            if (batteryState.batteryPresent > 0) {
                int estimatedTime = -2; // -1 = unknown, -2 = unlimited
                if (batteryState.acOnLine == 0 && batteryState.charging == 0 && batteryState.discharging > 0) {
                    estimatedTime = batteryState.estimatedTime;
                }
                long maxCapacity = FormatUtil.getUnsignedInt(batteryState.maxCapacity);
                long remainingCapacity = FormatUtil.getUnsignedInt(batteryState.remainingCapacity);
                psArray[0] = new WindowsPowerSource(name, (double) remainingCapacity / maxCapacity, estimatedTime);
            }
        }
        if (psArray[0] == null) {
            psArray[0] = new WindowsPowerSource("Unknown", 0d, -1d);
        }
        return psArray;
    }
}

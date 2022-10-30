package oshi.jna.platform.windows;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 * Power profile stats. This class should be considered non-API as it may be
 * removed if/when its code is incorporated into the JNA project.
 *
 * @author widdis[at]gmail[dot]com
 */
public interface PowrProf extends Library {
    PowrProf INSTANCE = Native.load("PowrProf", PowrProf.class);

    /**
     * Indicates power level information.
     */
    public interface POWER_INFORMATION_LEVEL {
        int LAST_SLEEP_TIME = 15;
        int LAST_WAKE_TIME = 14;
        int PROCESSOR_INFORMATION = 11;
        int SYSTEM_BATTERY_STATE = 5;
        int SYSTEM_EXECUTION_STATE = 16;
        int SYSTEM_POWER_CAPABILITIES = 4;
        int SYSTEM_POWER_INFORMATION = 12;
        int SYSTEM_POWER_POLICY_AC = 0;
        int SYSTEM_POWER_POLICY_CURRENT = 8;
        int SYSTEM_POWER_POLICY_DC = 1;
        int SYSTEM_RESERVE_HIBER_FILE = 10;
    }

    /**
     * Contains information about the current state of the system battery.
     */
    @FieldOrder({ "acOnLine", "batteryPresent", "charging", "discharging", "spare1", "tag", "maxCapacity",
            "remainingCapacity", "rate", "estimatedTime", "defaultAlert1", "defaultAlert2" })
    class SystemBatteryState extends Structure {
        public byte acOnLine;
        public byte batteryPresent;
        public byte charging;
        public byte discharging;
        public byte[] spare1 = new byte[3];
        public byte tag;
        public int maxCapacity;
        public int remainingCapacity;
        public int rate;
        public int estimatedTime;
        public int defaultAlert1;
        public int defaultAlert2;

        public SystemBatteryState(Pointer p) {
            super(p);
            read();
        }

        public SystemBatteryState() {
            super();
        }
    }

    /**
     * Contains information about a processor.
     */
    @FieldOrder({ "number", "maxMhz", "currentMhz", "mhzLimit", "maxIdleState", "currentIdleState" })
    class ProcessorPowerInformation extends Structure {
        public int number;
        public int maxMhz;
        public int currentMhz;
        public int mhzLimit;
        public int maxIdleState;
        public int currentIdleState;

        public ProcessorPowerInformation(Pointer p) {
            super(p);
            read();
        }

        public ProcessorPowerInformation() {
            super();
        }
    }

    int CallNtPowerInformation(int informationLevel, Pointer lpInputBuffer, int nInputBufferSize,
            Pointer lpOutputBuffer, int nOutputBufferSize);
}

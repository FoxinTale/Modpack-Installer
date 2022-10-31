package oshi.hardware.platform.linux;

import oshi.hardware.GlobalMemory;
import oshi.hardware.common.AbstractHardwareAbstractionLayer;

public class LinuxHardwareAbstractionLayer extends AbstractHardwareAbstractionLayer {
    @Override
    public GlobalMemory getMemory() {
        if (this.memory == null) {
            this.memory = new LinuxGlobalMemory();
        }
        return this.memory;
    }

}

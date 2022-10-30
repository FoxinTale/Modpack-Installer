package oshi.hardware.platform.unix.solaris;

import oshi.hardware.GlobalMemory;
import oshi.hardware.common.AbstractHardwareAbstractionLayer;

public class SolarisHardwareAbstractionLayer extends AbstractHardwareAbstractionLayer {

    private static final long serialVersionUID = 1L;

    @Override
    public GlobalMemory getMemory() {
        if (this.memory == null) {
            this.memory = new SolarisGlobalMemory();
        }
        return this.memory;
    }

}

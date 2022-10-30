package oshi.hardware.platform.mac;

import oshi.hardware.GlobalMemory;
import oshi.hardware.common.AbstractHardwareAbstractionLayer;

public class MacHardwareAbstractionLayer extends AbstractHardwareAbstractionLayer {

    private static final long serialVersionUID = 1L;
    @Override
    public GlobalMemory getMemory() {
        if (this.memory == null) {
            this.memory = new MacGlobalMemory();
        }
        return this.memory;
    }

}

package oshi.hardware.platform.windows;

import oshi.hardware.GlobalMemory;
import oshi.hardware.common.AbstractHardwareAbstractionLayer;

public class WindowsHardwareAbstractionLayer extends AbstractHardwareAbstractionLayer {
    private static final long serialVersionUID = 1L;

    @Override
    public GlobalMemory getMemory() {
        if (this.memory == null) {
            this.memory = new WindowsGlobalMemory();
        }
        return this.memory;
    }

}

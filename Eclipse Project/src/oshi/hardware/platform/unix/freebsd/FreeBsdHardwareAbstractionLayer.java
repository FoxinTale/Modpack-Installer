package oshi.hardware.platform.unix.freebsd;

import oshi.hardware.GlobalMemory;
import oshi.hardware.common.AbstractHardwareAbstractionLayer;

public class FreeBsdHardwareAbstractionLayer extends AbstractHardwareAbstractionLayer {

    private static final long serialVersionUID = 1L;

    @Override
    public GlobalMemory getMemory() {
        if (this.memory == null) {
            this.memory = new FreeBsdGlobalMemory();
        }
        return this.memory;
    }
}

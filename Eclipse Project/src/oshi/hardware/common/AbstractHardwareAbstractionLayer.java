package oshi.hardware.common;

import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * Common fields or methods used by platform-specific implementations of
 * HardwareAbstractionLayer
 */
public abstract class AbstractHardwareAbstractionLayer implements HardwareAbstractionLayer {

    private static final long serialVersionUID = 1L;
    protected GlobalMemory memory;


}

package oshi.hardware;

import java.io.Serializable;

/**
 * A hardware abstraction layer. Provides access to hardware items such as
 * processors, memory, battery, and disks.
 *
 * @author dblock[at]dblock[dot]org
 */
public interface HardwareAbstractionLayer extends Serializable {

    /**
     * Instantiates a {@link GlobalMemory} object.
     *
     * @return A memory object.
     */
    GlobalMemory getMemory();

}

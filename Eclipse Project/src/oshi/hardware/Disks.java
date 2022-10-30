package oshi.hardware;

import java.io.Serializable;

/**
 * Disks refers to hard drives installed in the machine.
 *
 * @author enrico[dot]bianchi[at]gmail[dot]com
 */
public interface Disks extends Serializable {

    /**
     * Get hard drives on this machine
     *
     * @return Array of {@link HWDiskStore} objects
     */
    HWDiskStore[] getDisks();
}

package oshi.hardware;

import java.io.Serializable;

/**
 * The GlobalMemory class tracks information about the use of a computer's
 * physical memory (RAM) as well as any available virtual memory.
 */
public interface GlobalMemory extends Serializable {
    /**
     * The amount of actual physical memory, in bytes.
     *
     * @return Total number of bytes.
     */
    long getTotal();

}

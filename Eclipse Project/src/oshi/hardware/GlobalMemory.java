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

    /**
     * The amount of physical memory currently available, in bytes.
     *
     * @return Available number of bytes.
     */
    long getAvailable();

    /**
     * The number of bytes in a memory page
     *
     * @return Page size in bytes.
     */
    long getPageSize();

    /**
     * Virtual memory, such as a swap file.
     * 
     * @return A VirtualMemory object.
     */
    VirtualMemory getVirtualMemory();

    /**
     * Update the values for the next call to the getters on this class.
     */
    void updateAttributes();
}

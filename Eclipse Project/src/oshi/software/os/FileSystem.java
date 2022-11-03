package oshi.software.os;

import java.io.Serializable;

/**
 * The File System is a logical arrangement, usually in a hierarchial tree,
 * where files are placed for storage and retrieval. It may consist of one or
 * more file stores.
 *
 * @author widdis[at]gmail[dot]com
 */
public interface FileSystem extends Serializable {

    /**
     * Get file stores on this machine
     *
     * Instantiates an array of {@link OSFileStore} objects, representing a
     * storage pool, device, partition, volume, concrete file system or other
     * implementation specific means of file storage.
     *
     * @return An array of OSFileStore objects or an empty array if none are
     *         present.
     */
    OSFileStore[] getFileStores();

    /**
     * The current number of open file descriptors. A file descriptor is an
     * abstract handle used to access I/O resources such as files and network
     * connections. On UNIX-based systems there is a system-wide limit on the
     * number of open file descriptors.
     *
     * On Windows systems, this method returns the total number of handles held
     * by Processes. While Windows handles are conceptually similar to file
     * descriptors, they may also refer to a number of non-I/O related objects.
     *
     * @return The number of open file descriptors if available, 0 otherwise.
     */
    long getOpenFileDescriptors();

    /**
     * The maximum number of open file descriptors. A file descriptor is an
     * abstract handle used to access I/O resources such as files and network
     * connections. On UNIX-based systems there is a system-wide limit on the
     * number of open file descriptors.
     *
     * On Windows systems, this method returns the theoretical max number of
     * handles (2^24-2^15 on 32-bit, 2^24-2^16 on 64-bit). There may be a lower
     * per-process limit. While Windows handles are conceptually similar to file
     * descriptors, they may also refer to a number of non-I/O related objects.
     *
     * @return The maximum number of file descriptors if available, 0 otherwise.
     */
    long getMaxFileDescriptors();
}
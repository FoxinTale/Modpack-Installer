package oshi.jna.platform.unix.freebsd;

import com.sun.jna.Native;

import oshi.jna.platform.unix.CLibrary;

/**
 * C library. This class should be considered non-API as it may be removed
 * if/when its code is incorporated into the JNA project.
 *
 * @author widdis[at]gmail[dot]com
 */
public interface Libc extends CLibrary {
    Libc INSTANCE = Native.load("libc", Libc.class);

    /*
     * Data size
     */
    int UINT64_SIZE = Native.getNativeSize(long.class);
    int INT_SIZE = Native.getNativeSize(int.class);

}

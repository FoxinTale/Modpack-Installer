package oshi.jna.platform.linux;

import com.sun.jna.Native;

import oshi.jna.platform.unix.CLibrary;

/**
 * Linux C Library. This class should be considered non-API as it may be removed
 * if/when its code is incorporated into the JNA project.
 *
 * @author widdis[at]gmail[dot]com
 */
public interface Libc extends CLibrary {

    Libc INSTANCE = Native.load("c", Libc.class);

}

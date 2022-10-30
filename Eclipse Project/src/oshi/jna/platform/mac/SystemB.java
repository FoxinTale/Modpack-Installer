package oshi.jna.platform.mac;

import com.sun.jna.Native;

import oshi.jna.platform.unix.CLibrary;

/**
 * System class. This class should be considered non-API as it may be removed
 * if/when its code is incorporated into the JNA project.
 *
 * @author widdis[at]gmail[dot]com
 */
public interface SystemB extends CLibrary, com.sun.jna.platform.mac.SystemB {
    SystemB INSTANCE = Native.load("System", SystemB.class);
}

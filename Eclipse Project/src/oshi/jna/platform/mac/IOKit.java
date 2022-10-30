
package oshi.jna.platform.mac;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import oshi.jna.platform.mac.CoreFoundation.CFAllocatorRef;
import oshi.jna.platform.mac.CoreFoundation.CFMutableDictionaryRef;
import oshi.jna.platform.mac.CoreFoundation.CFStringRef;
import oshi.jna.platform.mac.CoreFoundation.CFTypeRef;

/**
 * Power Supply stats. This class should be considered non-API as it may be
 * removed if/when its code is incorporated into the JNA project.
 *
 * @author widdis[at]gmail[dot]com
 */
public interface IOKit extends Library {
    IOKit INSTANCE = Native.load("IOKit", IOKit.class);


    class MachPort extends IntByReference {
    }

    int IOMasterPort(int unused, IntByReference masterPort);

    CFMutableDictionaryRef IOServiceMatching(String name);

    CFMutableDictionaryRef IOBSDNameMatching(int masterPort, int options, String bsdName);

    int IOServiceGetMatchingService(int port, CFMutableDictionaryRef matchingDictionary);

    int IOServiceGetMatchingServices(int port, CFMutableDictionaryRef matchingDictionary, IntByReference iterator);

    void IOObjectRelease(int object);

    int IOIteratorNext(int iterator);

    boolean IOObjectConformsTo(int object, String className);

    CFTypeRef IORegistryEntryCreateCFProperty(int entry, CFStringRef key, CFAllocatorRef allocator, int options);

}

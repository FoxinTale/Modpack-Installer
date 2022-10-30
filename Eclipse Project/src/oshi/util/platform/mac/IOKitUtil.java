package oshi.util.platform.mac;

import com.sun.jna.ptr.IntByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.jna.platform.mac.CoreFoundation;
import oshi.jna.platform.mac.CoreFoundation.CFMutableDictionaryRef;
import oshi.jna.platform.mac.CoreFoundation.CFStringRef;
import oshi.jna.platform.mac.CoreFoundation.CFTypeRef;
import oshi.jna.platform.mac.IOKit;
import oshi.jna.platform.mac.IOKit.MachPort;

/**
 * Provides utilities for IOKit
 *
 * @author widdis[at]gmail[dot]com
 */
public class IOKitUtil {
    private static final Logger LOG = LoggerFactory.getLogger(IOKitUtil.class);

    private static MachPort masterPort = new MachPort();

    private IOKitUtil() {
    }

    /**
     * Sets the masterPort value
     *
     * @return 0 if the value was successfully set, error value otherwise
     */
    private static int setMasterPort() {
        if (masterPort.getValue() == 0) {
            int result = IOKit.INSTANCE.IOMasterPort(0, masterPort);
            if (result != 0) {
                if (LOG.isErrorEnabled()) {
                    LOG.error(String.format("Error: IOMasterPort() = %08x", result));
                }
                return result;
            }
        }
        return 0;
    }

    /**
     * Opens an IOService matching the given name
     *
     * @param serviceName
     *            The service name to match
     * @return an int handle to an IOService if successful, 0 if failed
     */
    public static int getMatchingService(String serviceName) {
        if (setMasterPort() == 0) {
            int service = IOKit.INSTANCE.IOServiceGetMatchingService(masterPort.getValue(),
                    IOKit.INSTANCE.IOServiceMatching(serviceName));
            if (service == 0) {
                LOG.error("No service found: {}", serviceName);
            }
            return service;
        }
        return 0;
    }

    /**
     * Convenience method to get matching IOService objects
     *
     * @param matchingDictionary
     *            The dictionary to match
     * @param serviceIterator
     *            An interator over matching items, set on return
     * @return 0 if successful, an error code if failed.
     */
    public static int getMatchingServices(CFMutableDictionaryRef matchingDictionary, IntByReference serviceIterator) {
        int setMasterPort = setMasterPort();
        if (setMasterPort == 0) {
            return IOKit.INSTANCE.IOServiceGetMatchingServices(masterPort.getValue(), matchingDictionary,
                    serviceIterator);
        }
        return setMasterPort;
    }

    /**
     * Convenience method to get a String value from an IO Registry
     *
     * @param entry
     *            A handle to the registry entry
     * @param key
     *            The string name of the key to retrieve
     * @return The value of the registry entry if it exists; null otherwise
     */
    public static String getIORegistryStringProperty(int entry, String key) {
        String value = null;
        CFStringRef keyAsCFString = CfUtil.getCFString(key);
        CFTypeRef valueAsCFString = IOKit.INSTANCE.IORegistryEntryCreateCFProperty(entry, keyAsCFString,
                CoreFoundation.INSTANCE.CFAllocatorGetDefault(), 0);
        if (valueAsCFString != null && valueAsCFString.getPointer() != null) {
            value = CfUtil.cfPointerToString(valueAsCFString.getPointer());
        }
        CfUtil.release(valueAsCFString);
        return value;
    }

    /**
     * Convenience method to get the IO dictionary matching a bsd name
     *
     * @param bsdName
     *            The bsd name of the registry entry
     * @return The dictionary ref
     */
    public static CFMutableDictionaryRef getBSDNameMatchingDict(String bsdName) {
        if (setMasterPort() == 0) {
            return IOKit.INSTANCE.IOBSDNameMatching(masterPort.getValue(), 0, bsdName);
        }
        return null;
    }
}
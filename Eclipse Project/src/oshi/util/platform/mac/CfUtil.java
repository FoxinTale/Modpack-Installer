package oshi.util.platform.mac;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import oshi.jna.platform.mac.CoreFoundation;
import oshi.jna.platform.mac.CoreFoundation.CFAllocatorRef;
import oshi.jna.platform.mac.CoreFoundation.CFStringRef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides utilities for Core Foundations
 *
 * @author widdis[at]gmail[dot]com
 */
public class CfUtil {
    public static final CFAllocatorRef ALLOCATOR = CoreFoundation.INSTANCE.CFAllocatorGetDefault();

    /**
     * Cache cfStrings
     */
    private static Map<String, CFStringRef> cfStringMap = new ConcurrentHashMap<>();

    /**
     * Return a CFStringRef representing a string, caching the result
     *
     * @param key
     *            The string, usually a registry key
     * @return the corresponding CFString
     */
    public static CFStringRef getCFString(String key) {
        synchronized (cfStringMap) {
            CFStringRef value = cfStringMap.get(key);
            if (value != null) {
                return value;
            }
            value = CFStringRef.toCFString(key);
            cfStringMap.put(key, value);
            return value;
        }
    }

    /**
     * Enum values used for number type in CFNumberGetValue(). Use ordinal() to
     * fetch the corresponding constant.
     */
    public enum CFNumberType {
        unusedZero, kCFNumberSInt8Type, kCFNumberSInt16Type, kCFNumberSInt32Type, kCFNumberSInt64Type, kCFNumberFloat32Type, kCFNumberFloat64Type, kCFNumberCharType, kCFNumberShortType, kCFNumberIntType, kCFNumberLongType, kCFNumberLongLongType, kCFNumberFloatType, kCFNumberDoubleType, kCFNumberCFIndexType, kCFNumberNSIntegerType, kCFNumberCGFloatType, kCFNumberMaxType
    }

    /**
     * Convert a pointer representing a Core Foundations String into its string
     *
     * @param p
     *            The pointer to a CFString
     * @return The corresponding string
     */
    public static String cfPointerToString(Pointer p) {
        if (p == null) {
            return "null";
        }
        long length = CoreFoundation.INSTANCE.CFStringGetLength(p);
        long maxSize = CoreFoundation.INSTANCE.CFStringGetMaximumSizeForEncoding(length, CoreFoundation.UTF_8);
        if (maxSize == 0) {
            maxSize = 1;
        }
        Pointer buf = new Memory(maxSize);
        CoreFoundation.INSTANCE.CFStringGetCString(p, buf, maxSize, CoreFoundation.UTF_8);
        return buf.getString(0);
    }

    /**
     * Releases a CF reference. Mandatory when an object is owned (using
     * 'create' or 'copy' methods).
     *
     * @param ref
     *            The reference to release
     */
    public static void release(PointerType ref) {
        if (ref != null) {
            CoreFoundation.INSTANCE.CFRelease(ref);
        }
    }

}
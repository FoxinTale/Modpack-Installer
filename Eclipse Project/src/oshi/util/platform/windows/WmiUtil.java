package oshi.util.platform.windows;

import com.sun.jna.platform.win32.Variant; // NOSONAR
import com.sun.jna.platform.win32.COM.Wbemcli;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;

import oshi.util.ParseUtil;

/**
 * Helper class for WMI
 *
 * @author widdis[at]gmail[dot]com
 */
public class WmiUtil {

    // Not a built in manespace, failed connections are normal and don't need
    // error logging
    public static final String OHM_NAMESPACE = "ROOT\\OpenHardwareMonitor";

    private static final String CLASS_CAST_MSG = "%s is not a %s type. CIM Type is %d and VT type is %d";

    /**
     * Private constructor so this class can't be instantiated from the outside.
     */
    private WmiUtil() {
    }

    /**
     * Translate a WmiQuery to the actual query string
     * 
     * @param <T>
     *            The properties enum
     * @param query
     *            The WmiQuery object
     * @return The string that is queried in WMI
     */
    public static <T extends Enum<T>> String queryToString(WmiQuery<T> query) {
        T[] props = query.getPropertyEnum().getEnumConstants();
        StringBuilder sb = new StringBuilder("SELECT ");
        sb.append(props[0].name());
        for (int i = 1; i < props.length; i++) {
            sb.append(',').append(props[i].name());
        }
        sb.append(" FROM ").append(query.getWmiClassName());
        return sb.toString();
    }

    /**
     * Gets a String value from a WmiResult
     *
     * @param <T>
     *            The enum type containing the property keys
     * @param result
     *            The WmiResult from which to fetch the value
     * @param property
     *            The property (column) to fetch
     * @param index
     *            The index (row) to fetch
     * @return The stored value if non-null, an empty-string otherwise
     */
    public static <T extends Enum<T>> String getString(WmiResult<T> result, T property, int index) {
        if (result.getCIMType(property) == Wbemcli.CIM_STRING) {
            return getStr(result, property, index);
        }
        throw new ClassCastException(String.format(CLASS_CAST_MSG, property.name(), "String",
                result.getCIMType(property), result.getVtType(property)));
    }

    private static <T extends Enum<T>> String getStr(WmiResult<T> result, T property, int index) {
        Object o = result.getValue(property, index);
        if (o == null) {
            return "";
        } else if (result.getVtType(property) == Variant.VT_BSTR) {
            return (String) o;
        }
        throw new ClassCastException(String.format(CLASS_CAST_MSG, property.name(), "String-mapped",
                result.getCIMType(property), result.getVtType(property)));
    }

    /**
     * Gets a Uint64 value from a WmiResult (parsing the String). Note that
     * while the CIM type is unsigned, the return type is signed and the parsing
     * will exclude any return values above Long.MAX_VALUE.
     *
     * @param <T>
     *            The enum type containing the property keys
     * @param result
     *            The WmiResult from which to fetch the value
     * @param property
     *            The property (column) to fetch
     * @param index
     *            The index (row) to fetch
     * @return The stored value if non-null and parseable as a long, 0 otherwise
     */
    public static <T extends Enum<T>> long getUint64(WmiResult<T> result, T property, int index) {
        Object o = result.getValue(property, index);
        if (o == null) {
            return 0L;
        } else if (result.getCIMType(property) == Wbemcli.CIM_UINT64 && result.getVtType(property) == Variant.VT_BSTR) {
            return ParseUtil.parseLongOrDefault((String) o, 0L);
        }
        throw new ClassCastException(String.format(CLASS_CAST_MSG, property.name(), "UINT64",
                result.getCIMType(property), result.getVtType(property)));
    }

    /**
     * Gets an UINT32 value from a WmiResult. Note that while a UINT32 CIM type
     * is unsigned, the return type is signed and requires further processing by
     * the user if unsigned values are desired.
     *
     * @param <T>
     *            The enum type containing the property keys
     * @param result
     *            The WmiResult from which to fetch the value
     * @param property
     *            The property (column) to fetch
     * @param index
     *            The index (row) to fetch
     * @return The stored value if non-null, 0 otherwise
     */
    public static <T extends Enum<T>> int getUint32(WmiResult<T> result, T property, int index) {
        if (result.getCIMType(property) == Wbemcli.CIM_UINT32) {
            return getInt(result, property, index);
        }
        throw new ClassCastException(String.format(CLASS_CAST_MSG, property.name(), "UINT32",
                result.getCIMType(property), result.getVtType(property)));
    }

    /**
     * Gets an UINT32 value from a WmiResult as a long, preserving the
     * unsignedness.
     *
     * @param <T>
     *            The enum type containing the property keys
     * @param result
     *            The WmiResult from which to fetch the value
     * @param property
     *            The property (column) to fetch
     * @param index
     *            The index (row) to fetch
     * @return The stored value if non-null, 0 otherwise
     */
    public static <T extends Enum<T>> long getUint32asLong(WmiResult<T> result, T property, int index) {
        if (result.getCIMType(property) == Wbemcli.CIM_UINT32) {
            return getInt(result, property, index) & 0xFFFFFFFFL;
        }
        throw new ClassCastException(String.format(CLASS_CAST_MSG, property.name(), "UINT32",
                result.getCIMType(property), result.getVtType(property)));
    }

    /**
     * Gets a Uint16 value from a WmiResult. Note that while the CIM type is
     * unsigned, the return type is signed and requires further processing by
     * the user if unsigned values are desired.
     *
     * @param <T>
     *            The enum type containing the property keys
     * @param result
     *            The WmiResult from which to fetch the value
     * @param property
     *            The property (column) to fetch
     * @param index
     *            The index (row) to fetch
     * @return The stored value if non-null, 0 otherwise
     */
    public static <T extends Enum<T>> int getUint16(WmiResult<T> result, T property, int index) {
        if (result.getCIMType(property) == Wbemcli.CIM_UINT16) {
            return getInt(result, property, index);
        }
        throw new ClassCastException(String.format(CLASS_CAST_MSG, property.name(), "UINT16",
                result.getCIMType(property), result.getVtType(property)));
    }

    private static <T extends Enum<T>> int getInt(WmiResult<T> result, T property, int index) {
        Object o = result.getValue(property, index);
        if (o == null) {
            return 0;
        } else if (result.getVtType(property) == Variant.VT_I4) {
            return (int) o;
        }
        throw new ClassCastException(String.format(CLASS_CAST_MSG, property.name(), "32-bit integer",
                result.getCIMType(property), result.getVtType(property)));
    }

}

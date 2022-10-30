package oshi.util.platform.windows;

import com.sun.jna.platform.win32.COM.COMException;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Wbemcli;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.WinError;
import com.sun.jna.platform.win32.WinNT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class WmiQueryHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WmiQueryHandler.class);

    // Timeout for WMI queries
    private int wmiTimeout = Wbemcli.WBEM_INFINITE;

    // Cache failed wmi classes
    private final Set<String> failedWmiClassNames = new HashSet<>();

    // Preferred threading model
    private int comThreading = Ole32.COINIT_MULTITHREADED;

    // Track initialization of Security
    private boolean securityInitialized = false;

    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    // Factory to create this or a subclass
    private static Class<? extends WmiQueryHandler> customClass = null;


    public static WmiQueryHandler createInstance() {
        if (customClass == null) {
            return new WmiQueryHandler();
        }
        try {
            return customClass.getConstructor(EMPTY_CLASS_ARRAY).newInstance(EMPTY_OBJECT_ARRAY);
        } catch (NoSuchMethodException | SecurityException e) {
            LOG.error("Failed to find or access a no-arg constructor for {}", customClass);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            LOG.error("Failed to create a new instance of {}", customClass);
        }
        return null;
    }


    /**
     * Query WMI for values, with no timeout.
     *
     * @param <T>
     *            The properties enum
     * @param query
     *            A WmiQuery object encapsulating the namespace, class, and
     *            properties
     * @return a WmiResult object containing the query results, wrapping an
     *         EnumMap
     */
    public <T extends Enum<T>> WbemcliUtil.WmiResult<T> queryWMI(WbemcliUtil.WmiQuery<T> query) {

        WbemcliUtil.WmiResult<T> result = WbemcliUtil.INSTANCE.new WmiResult<>(query.getPropertyEnum());
        if (failedWmiClassNames.contains(query.getWmiClassName())) {
            return result;
        }
        boolean comInit = false;
        try {
            comInit = initCOM();
            result = query.execute(wmiTimeout);
        } catch (COMException e) {
            // Ignore any exceptions with OpenHardwareMonitor
            if (!WmiUtil.OHM_NAMESPACE.equals(query.getNameSpace())) {
                final int hresult = e.getHresult() == null ? -1 : e.getHresult().intValue();
                switch (hresult) {
                case Wbemcli.WBEM_E_INVALID_NAMESPACE:
                    LOG.warn("COM exception: Invalid Namespace {}", query.getNameSpace());
                    break;
                case Wbemcli.WBEM_E_INVALID_CLASS:
                    LOG.warn("COM exception: Invalid Class {}", query.getWmiClassName());
                    break;
                case Wbemcli.WBEM_E_INVALID_QUERY:
                    LOG.warn("COM exception: Invalid Query: {}", WmiUtil.queryToString(query));
                    break;
                default:
                    handleComException(query, e);
                    break;
                }
                failedWmiClassNames.add(query.getWmiClassName());
            }
        } catch (TimeoutException e) {
            LOG.error("WMI query timed out after {} ms: {}", wmiTimeout, WmiUtil.queryToString(query));
        }
        if (comInit) {
            unInitCOM();
        }
        return result;
    }

    protected void handleComException(WbemcliUtil.WmiQuery<?> query, COMException ex) {
        LOG.warn(
                "COM exception querying {}, which might not be on your system. Will not attempt to query it again. Error was: {}:",
                query.getWmiClassName(), ex.getMessage());
    }

    /**
     * Initializes COM library and sets security to impersonate the local user
     * 
     * @return True if COM was initialized and needs to be uninitialized, false
     *         otherwise
     */
    public boolean initCOM() {
        boolean comInit = false;
        // Step 1: --------------------------------------------------
        // Initialize COM. ------------------------------------------
        comInit = initCOM(getComThreading());
        if (!comInit) {
            comInit = initCOM(switchComThreading());
        }
        // Step 2: --------------------------------------------------
        // Set general COM security levels --------------------------
        if (comInit && !isSecurityInitialized()) {
            WinNT.HRESULT hres = Ole32.INSTANCE.CoInitializeSecurity(null, -1, null, null,
                    Ole32.RPC_C_AUTHN_LEVEL_DEFAULT, Ole32.RPC_C_IMP_LEVEL_IMPERSONATE, null, Ole32.EOAC_NONE, null);
            // If security already initialized we get RPC_E_TOO_LATE
            // This can be safely ignored
            if (COMUtils.FAILED(hres) && hres.intValue() != WinError.RPC_E_TOO_LATE) {
                Ole32.INSTANCE.CoUninitialize();
                throw new COMException("Failed to initialize security.", hres);
            }
            securityInitialized = true;
        }
        return comInit;
    }

    protected boolean initCOM(int coInitThreading) {
        WinNT.HRESULT hres = Ole32.INSTANCE.CoInitializeEx(null, coInitThreading);
        switch (hres.intValue()) {
        // Successful local initialization (S_OK) or was already initialized
        // (S_FALSE) but still needs uninit
        case COMUtils.S_OK:
        case COMUtils.S_FALSE:
            return true;
        // COM was already initialized with a different threading model
        case WinError.RPC_E_CHANGED_MODE:
            return false;
        // Any other results is impossible
        default:
            throw new COMException("Failed to initialize COM library.", hres);
        }
    }

    /**
     * UnInitializes COM library. This should be called once for every
     * successful call to initCOM.
     */
    public void unInitCOM() {
        Ole32.INSTANCE.CoUninitialize();
    }

    /**
     * Returns the current threading model for COM initialization, as OSHI is
     * required to match if an external program has COM initialized already.
     * 
     * @return The current threading model
     */
    public int getComThreading() {
        return comThreading;
    }

    /**
     * Switches the current threading model for COM initialization, as OSHI is
     * required to match if an external program has COM initialized already.
     * 
     * @return The new threading model after switching
     */
    public int switchComThreading() {
        if (comThreading == Ole32.COINIT_APARTMENTTHREADED) {
            comThreading = Ole32.COINIT_MULTITHREADED;
        } else {
            comThreading = Ole32.COINIT_APARTMENTTHREADED;
        }
        return comThreading;
    }

    /**
     * Security only needs to be initialized once. This boolean identifies
     * whether that has happened.
     *
     * @return Returns the securityInitialized.
     */
    public boolean isSecurityInitialized() {
        return securityInitialized;
    }

}

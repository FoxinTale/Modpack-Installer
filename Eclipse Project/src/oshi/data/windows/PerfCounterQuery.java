package oshi.data.windows;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import slf4j.Logger;
import slf4j.LoggerFactory;
import oshi.util.platform.windows.PerfDataUtil;
import oshi.util.platform.windows.PerfDataUtil.PerfCounter;
import oshi.util.platform.windows.WmiQueryHandler;

import java.util.EnumMap;

public class PerfCounterQuery<T extends Enum<T>> {

    private static final Logger LOG = LoggerFactory.getLogger(PerfCounter.class);

    /*
     * Set on instantiation
     */
    protected final Class<T> propertyEnum;
    protected final String perfObject;
    protected final String perfWmiClass;
    protected final String queryKey;
    protected CounterDataSource source;
    protected PerfCounterQueryHandler pdhQueryHandler;
    protected WmiQueryHandler wmiQueryHandler;
    /*
     * Only one will be non-null depending on source
     */
    private EnumMap<T, PerfCounter> counterMap = null;
    protected WmiQuery<T> counterQuery = null;

    /*
     * Multiple classes use these constants
     */
    public static final String TOTAL_INSTANCE = "_Total";


    /**
     * Construct a new object to hold performance counter data source and
     * results
     * 
     * @param propertyEnum
     *            An enum which implements {@link PdhCounterProperty} and
     *            contains the WMI field (Enum value) and PDH Counter string
     *            (instance and counter)
     * @param perfObject
     *            The PDH object for this counter; all counters on this object
     *            will be refreshed at the same time
     * @param perfWmiClass
     *            The WMI PerfData_RawData_* class corresponding to the PDH
     *            object
     * @param queryKey
     *            An optional key for PDH counter updates; defaults to the PDH
     *            object name
     */
    public PerfCounterQuery(Class<T> propertyEnum, String perfObject, String perfWmiClass, String queryKey) {
        this.propertyEnum = propertyEnum;
        this.perfObject = perfObject;
        this.perfWmiClass = perfWmiClass;
        this.queryKey = queryKey;
        this.pdhQueryHandler = PerfCounterQueryHandler.getInstance();
        this.wmiQueryHandler = WmiQueryHandler.createInstance();
        // Start off with PDH as source; if query here fails we will permanently
        // fall back to WMI
        this.source = CounterDataSource.PDH;
    }

    /**
     * Set the Data Source for these counters
     *
     * @param source The source of data
     */
    public void setDataSource(CounterDataSource source) {
        this.source = source;
        switch (source) {
        case PDH:
            LOG.debug("Attempting to set PDH Data Source.");
            unInitWmiCounters();
            initPdhCounters();
            return;
        case WMI:
            LOG.debug("Attempting to set WMI Data Source.");
            unInitPdhCounters();
            initWmiCounters();
            return;
        default:
            // This should never happen unless you've added a new source and
            // forgot to add a case for it
            throw new IllegalArgumentException("Invalid Data Source specified.");
        }
    }

    /**
     * Initialize PDH counters for this data source. Adds necessary counters to
     * a PDH Query.
     * 
     * @return True if the counters were successfully added.
     */
    protected boolean initPdhCounters() {
        this.counterMap = new EnumMap<>(propertyEnum);
        for (T prop : propertyEnum.getEnumConstants()) {
            PerfCounter counter = PerfDataUtil.createCounter(perfObject, ((PdhCounterProperty) prop).getInstance(),
                    ((PdhCounterProperty) prop).getCounter());
            counterMap.put(prop, counter);
            if (!pdhQueryHandler.addCounterToQuery(counter, this.queryKey)) {
                unInitPdhCounters();
                return false;
            }
        }
        return true;
    }

    /**
     * Uninitialize PDH counters for this data source. Removes necessary
     * counters from the PDH Query, releasing their handles.
     */
    protected void unInitPdhCounters() {
        pdhQueryHandler.removeAllCountersFromQuery(this.queryKey);
        this.counterMap = null;
    }

    /**
     * Initialize the WMI query object needed to retrieve counters for this data
     * source.
     */
    protected void initWmiCounters() {
        this.counterQuery = new WmiQuery<>(perfWmiClass, propertyEnum);
    }

    /**
     * Uninitializes the WMI query object needed to retrieve counters for this
     * data source, allowing it to be garbage collected.
     */
    protected void unInitWmiCounters() {
        this.counterQuery = null;
    }

    /**
     * Source of performance counter data.
     */
    public enum CounterDataSource {
        /**
         * Performance Counter data will be pulled from a PDH Counter
         */
        PDH,
        /**
         * Performance Counter data will be pulled from a WMI PerfData_RawData_*
         * table
         */
        WMI;
    }

    /**
     * Contract for Counter Property Enums
     */
    public interface PdhCounterProperty {
        /**
         * @return Returns the instance.
         */
        String getInstance();

        /**
         * @return Returns the counter.
         */
        String getCounter();
    }
}

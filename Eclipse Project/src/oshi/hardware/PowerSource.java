package oshi.hardware;

import java.io.Serializable;

/**
 * The Power Source is one or more batteries with some capacity, and some state
 * of charge/discharge
 *
 * @author widdis[at]gmail[dot]com
 */
public interface PowerSource extends Serializable {
    /**
     * Name of the power source (e.g., InternalBattery-0)
     *
     * @return The power source name
     */
    String getName();

    /**
     * Remaining capacity as a fraction of max capacity.
     *
     * @return A value between 0.0 (fully drained) and 1.0 (fully charged)
     */
    double getRemainingCapacity();

    /**
     * Estimated time remaining on the power source, in seconds.
     *
     * @return If positive, seconds remaining. If negative, -1.0 (calculating)
     *         or -2.0 (unlimited)
     */
    double getTimeRemaining();
}

package oshi.hardware;

import java.io.Serializable;

/**
 * A USB device is a device connected via a USB port, possibly
 * internally/permanently. Hubs may contain ports to which other devices connect
 * in a recursive fashion.
 *
 * @author widdis[at]gmail[dot]com
 */
public interface UsbDevice extends Serializable, Comparable<UsbDevice> {
    /**
     * Name of the USB device
     *
     * @return The device name
     */
    String getName();

    /**
     * Vendor that manufactured the USB device
     *
     * @return The vendor name
     */
    String getVendor();

    /**
     * ID of the vendor that manufactured the USB device
     *
     * @return The vendor ID, a 4-digit hex string
     */
    String getVendorId();

    /**
     * Product ID of the USB device
     *
     * @return The product ID, a 4-digit hex string
     */
    String getProductId();

    /**
     * Serial number of the USB device
     *
     * @return The serial number, if known
     */
    String getSerialNumber();

    /**
     * Other devices connected to this hub
     *
     * @return An array of other devices connected to this hub, if any, or an
     *         empty array if none
     */
    UsbDevice[] getConnectedDevices();
}

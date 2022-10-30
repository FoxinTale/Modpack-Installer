package oshi.hardware;

import java.io.Serializable;

/**
 * The Baseboard represents the system board, also called motherboard, logic
 * board, etc.
 */
public interface Baseboard extends Serializable {
    /**
     * Get the baseboard manufacturer.
     *
     * @return The manufacturer.
     */
    String getManufacturer();

    /**
     * Get the baseboard model.
     *
     * @return The model.
     */
    String getModel();

    /**
     * Get the baseboard version.
     *
     * @return The version.
     */
    String getVersion();

    /**
     * Get the baseboard serial number.
     *
     * @return The serial number.
     */
    String getSerialNumber();
}

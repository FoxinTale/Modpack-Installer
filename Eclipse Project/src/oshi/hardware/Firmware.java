package oshi.hardware;

import java.io.Serializable;

/**
 * The Firmware represents the low level BIOS or equivalent.
 */
public interface Firmware extends Serializable {

    /**
     * Get the firmware manufacturer.
     *
     * @return the manufacturer
     */
    String getManufacturer();

    /**
     * Get the firmware name.
     *
     * @return the name
     */
    String getName();

    /**
     * Get the firmware description.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Get the firmware version.
     *
     * @return the version
     */
    String getVersion();

    /**
     * Get the firmware release date.
     *
     * @return The release date.
     */
    String getReleaseDate();
}

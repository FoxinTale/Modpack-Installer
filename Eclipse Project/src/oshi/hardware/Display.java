package oshi.hardware;

import java.io.Serializable;

/**
 * Display refers to the information regarding a video source and monitor
 * identified by the EDID standard.
 *
 * @author widdis[at]gmail[dot]com
 */
public interface Display extends Serializable {
    /**
     * The EDID byte array.
     *
     * @return The original unparsed EDID byte array.
     */
    byte[] getEdid();
}

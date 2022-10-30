package oshi.software.os;

import java.io.Serializable;

public interface OperatingSystemVersion extends Serializable {
    /**
     * @return the version
     */
    String getVersion();

    /**
     * @param version
     *            the version to set
     */
    void setVersion(String version);

    /**
     * @return the codeName
     */
    String getCodeName();

    /**
     * @param codeName
     *            the codeName to set
     */
    void setCodeName(String codeName);

    /**
     * @return the build number
     */
    String getBuildNumber();

    /**
     * @param buildNumber
     *            the build number to set
     */
    void setBuildNumber(String buildNumber);
}

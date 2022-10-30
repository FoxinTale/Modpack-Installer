package oshi.software.common;

import oshi.software.os.OperatingSystemVersion;

/**
 * Contains operating system version information. The information includes major
 * and minor version numbers, a build number, a platform identifier, and
 * descriptive text about the operating system.
 */
public class AbstractOSVersionInfoEx implements OperatingSystemVersion {

    private static final long serialVersionUID = 1L;

    protected String version;

    protected String codeName;

    protected String versionStr;

    protected String buildNumber;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVersion() {
        return this.version;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCodeName() {
        return this.codeName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuildNumber() {
        return this.buildNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    @Override
    public String toString() {
        if (this.versionStr == null) {
            StringBuilder sb = new StringBuilder(getVersion() != null ? getVersion() : "Unknown");
            if (getCodeName().length() > 0) {
                sb.append(" (").append(getCodeName()).append(')');
            }
            if (getBuildNumber().length() > 0) {
                sb.append(" build ").append(getBuildNumber());
            }
            this.versionStr = sb.toString();
        }
        return this.versionStr;
    }
}

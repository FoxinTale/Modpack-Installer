package oshi.software.os.unix.solaris;

import oshi.software.common.AbstractNetworkParams;
import oshi.util.ExecutingCommand;

public class SolarisNetworkParams extends AbstractNetworkParams {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIpv4DefaultGateway() {
        return searchGateway(ExecutingCommand.runNative("route get -inet default"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIpv6DefaultGateway() {
        return searchGateway(ExecutingCommand.runNative("route get -inet6 default"));
    }
}
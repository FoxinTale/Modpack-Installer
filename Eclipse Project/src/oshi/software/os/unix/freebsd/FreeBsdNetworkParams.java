package oshi.software.os.unix.freebsd;

import com.sun.jna.ptr.PointerByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.jna.platform.unix.freebsd.Libc;
import oshi.software.common.AbstractNetworkParams;
import oshi.util.ExecutingCommand;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class FreeBsdNetworkParams extends AbstractNetworkParams {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(FreeBsdNetworkParams.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDomainName() {
        Libc.Addrinfo hint = new Libc.Addrinfo();
        hint.ai_flags = Libc.AI_CANONNAME;
        String hostname = "";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            LOG.error("Unknown host exception when getting address of local host: {}", e);
            return "";
        }
        PointerByReference ptr = new PointerByReference();
        int res = Libc.INSTANCE.getaddrinfo(hostname, null, hint, ptr);
        if (res > 0) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Failed getaddrinfo(): {}", Libc.INSTANCE.gai_strerror(res));
            }
            return "";
        }
        Libc.Addrinfo info = new Libc.Addrinfo(ptr.getValue());
        String canonname = info.ai_canonname.trim();
        Libc.INSTANCE.freeaddrinfo(ptr.getValue());
        return canonname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIpv4DefaultGateway() {
        return searchGateway(ExecutingCommand.runNative("route -4 get default"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIpv6DefaultGateway() {
        return searchGateway(ExecutingCommand.runNative("route -6 get default"));
    }
}
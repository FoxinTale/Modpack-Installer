package slf4j.nop;

import slf4j.ILoggerFactory;
import slf4j.IMarkerFactory;
import slf4j.helpers.BasicMarkerFactory;
import slf4j.helpers.NOPLoggerFactory;
import slf4j.helpers.NOPMDCAdapter;
import slf4j.spi.MDCAdapter;
import slf4j.spi.SLF4JServiceProvider;

public class NOPServiceProvider implements SLF4JServiceProvider {

    /**
     * Declare the version of the SLF4J API this implementation is compiled against. 
     * The value of this field is modified with each major release. 
     */
    // to avoid constant folding by the compiler, this field must *not* be final
    public static String REQUESTED_API_VERSION = "2.0.99"; // !final

    private final ILoggerFactory loggerFactory = new NOPLoggerFactory();
    private final IMarkerFactory markerFactory = new BasicMarkerFactory();
    private final MDCAdapter mdcAdapter = new NOPMDCAdapter();

    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    public IMarkerFactory getMarkerFactory() {
        return markerFactory;
    }

    public MDCAdapter getMDCAdapter() {
        return mdcAdapter;
    }

    @Override
    public String getRequestedApiVersion() {
        return REQUESTED_API_VERSION;
    }

    public void initialize() {

    }

   
}

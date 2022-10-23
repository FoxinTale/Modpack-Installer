package JsonUtils.XML;

import java.util.Collections;
import java.util.Map;

public class XMLParserConfiguration {

    public static final XMLParserConfiguration ORIGINAL
            = new XMLParserConfiguration();
    public static final XMLParserConfiguration KEEP_STRINGS
            = new XMLParserConfiguration().withKeepStrings(true);

    private boolean keepStrings;
    private String cDataTagName;
    private boolean convertNilAttributeToNull;
    private Map<String, XMLXsiTypeConverter<?>> xsiTypeMap;

    public XMLParserConfiguration() {
        this.keepStrings = false;
        this.cDataTagName = "content";
        this.convertNilAttributeToNull = false;
        this.xsiTypeMap = Collections.emptyMap();
    }

    @Deprecated
    public XMLParserConfiguration(final boolean keepStrings) {
        this(keepStrings, "content", false);
    }

    @Deprecated
    public XMLParserConfiguration(final String cDataTagName) {
        this(false, cDataTagName, false);
    }

    @Deprecated
    public XMLParserConfiguration(final boolean keepStrings, final String cDataTagName) {
        this.keepStrings = keepStrings;
        this.cDataTagName = cDataTagName;
        this.convertNilAttributeToNull = false;
    }

    @Deprecated
    public XMLParserConfiguration(final boolean keepStrings, final String cDataTagName, final boolean convertNilAttributeToNull) {
        this.keepStrings = keepStrings;
        this.cDataTagName = cDataTagName;
        this.convertNilAttributeToNull = convertNilAttributeToNull;
    }

    private XMLParserConfiguration(final boolean keepStrings, final String cDataTagName,
                                   final boolean convertNilAttributeToNull, final Map<String, XMLXsiTypeConverter<?>> xsiTypeMap) {
        this.keepStrings = keepStrings;
        this.cDataTagName = cDataTagName;
        this.convertNilAttributeToNull = convertNilAttributeToNull;
        this.xsiTypeMap = Collections.unmodifiableMap(xsiTypeMap);
    }

    @Override
    protected XMLParserConfiguration clone() {
        return new XMLParserConfiguration(
                this.keepStrings,
                this.cDataTagName,
                this.convertNilAttributeToNull,
                this.xsiTypeMap
        );
    }

    public boolean isKeepStrings() {
        return this.keepStrings;
    }

    public XMLParserConfiguration withKeepStrings(final boolean newVal) {
        XMLParserConfiguration newConfig = this.clone();
        newConfig.keepStrings = newVal;
        return newConfig;
    }

    public String getcDataTagName() {
        return this.cDataTagName;
    }

    public boolean isConvertNilAttributeToNull() {
        return this.convertNilAttributeToNull;
    }

    public Map<String, XMLXsiTypeConverter<?>> getXsiTypeMap() {
        return this.xsiTypeMap;
    }


}

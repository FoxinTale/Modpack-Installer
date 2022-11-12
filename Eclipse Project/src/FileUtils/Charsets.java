package FileUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.SortedMap;
import java.util.TreeMap;

public class Charsets {

    static {
        final SortedMap<String, Charset> standardCharsetMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        standardCharsetMap.put(StandardCharsets.ISO_8859_1.name(), StandardCharsets.ISO_8859_1);
        standardCharsetMap.put(StandardCharsets.US_ASCII.name(), StandardCharsets.US_ASCII);
        standardCharsetMap.put(StandardCharsets.UTF_16.name(), StandardCharsets.UTF_16);
        standardCharsetMap.put(StandardCharsets.UTF_16BE.name(), StandardCharsets.UTF_16BE);
        standardCharsetMap.put(StandardCharsets.UTF_16LE.name(), StandardCharsets.UTF_16LE);
        standardCharsetMap.put(StandardCharsets.UTF_8.name(), StandardCharsets.UTF_8);
    }

    public static Charset toCharset(final Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    public static Charset toCharset(final String charsetName) {
        return charsetName == null ? Charset.defaultCharset() : Charset.forName(charsetName);
    }

    @Deprecated
    public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;

    @Deprecated
    public static final Charset US_ASCII = StandardCharsets.US_ASCII;

    @Deprecated
    public static final Charset UTF_16 = StandardCharsets.UTF_16;

    @Deprecated
    public static final Charset UTF_16BE = StandardCharsets.UTF_16BE;

    @Deprecated
    public static final Charset UTF_16LE = StandardCharsets.UTF_16LE;

    @Deprecated
    public static final Charset UTF_8 = StandardCharsets.UTF_8;
}

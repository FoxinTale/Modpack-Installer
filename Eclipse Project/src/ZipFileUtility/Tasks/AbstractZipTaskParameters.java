package ZipFileUtility.Tasks;

import java.nio.charset.Charset;

public abstract class AbstractZipTaskParameters {
    protected Charset charset;
    protected AbstractZipTaskParameters(Charset charset) {
        this.charset = charset;
    }
}

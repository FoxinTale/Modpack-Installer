package FileUtils.OutputStream;

import java.io.IOException;
import java.io.OutputStream;

public class NullOutputStream extends OutputStream {

    @Deprecated
    public NullOutputStream() {
    }

    @Override
    public void write(final byte[] b, final int off, final int len) {
        // To /dev/null
    }

    @Override
    public void write(final int b) {
        // To /dev/null
    }

    @Override
    public void write(final byte[] b) throws IOException {
        // To /dev/null
    }

}

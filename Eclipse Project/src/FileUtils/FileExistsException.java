package FileUtils;

import java.io.IOException;

public class FileExistsException extends IOException {

    private static final long serialVersionUID = 1L;

    public FileExistsException(final String message) {
        super(message);
    }

}

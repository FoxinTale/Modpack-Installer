package FileUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class IOExceptionList extends IOException {

    private static final long serialVersionUID = 1L;
    private final List<? extends Throwable> causeList;

    public IOExceptionList(final List<? extends Throwable> causeList) {
        super(String.format("%,d exceptions: %s", causeList == null ? 0 : causeList.size(), causeList),
                causeList == null ? null : causeList.get(0));
        this.causeList = causeList == null ? Collections.emptyList() : causeList;
    }

    public <T extends Throwable> List<T> getCauseList() {
        return (List<T>) causeList;
    }
    public <T extends Throwable> T getCause(final int index) {
        return (T) causeList.get(index);
    }
    public <T extends Throwable> T getCause(final int index, final Class<T> clazz) {
        return (T) causeList.get(index);
    }
}

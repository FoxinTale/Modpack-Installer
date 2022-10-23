package FileUtils.Filters;

import FileUtils.IOFileFilter;

public interface ConditionalFileFilter {
    void addFileFilter(IOFileFilter ioFileFilter);
}

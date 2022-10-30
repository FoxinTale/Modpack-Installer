package oshi.hardware.common;

import oshi.hardware.GlobalMemory;

public abstract class AbstractGlobalMemory implements GlobalMemory {

    private static final long serialVersionUID = 1L;

    protected long memTotal = -1L;
    protected long memAvailable = -1L;
    protected long pageSize = -1L;


    @Override
    public long getTotal() {
        return this.memTotal;
    }

}

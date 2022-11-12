package SetUtils.Collections;

import SetUtils.Annotations.GwtCompatible;

import java.util.Iterator;

@GwtCompatible
public abstract class UnmodifiableIterator<E> implements Iterator<E> {

    protected UnmodifiableIterator() {
    }

    @Deprecated
    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}

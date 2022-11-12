package SetUtils.Collections;

import SetUtils.Annotations.CanIgnoreReturnValue;
import SetUtils.Annotations.GwtCompatible;
import SetUtils.Annotations.Nullable;
import SetUtils.Base.Preconditions;

import java.util.NoSuchElementException;

@GwtCompatible
public abstract class AbstractIterator<T> extends UnmodifiableIterator<T> {
    private State state = State.NOT_READY;

    protected AbstractIterator() {
    }

    private enum State {
        READY,
        NOT_READY,
        DONE,
        FAILED,
    }

    private @Nullable T next;

    protected abstract T computeNext();


    @CanIgnoreReturnValue
    protected final T endOfData() {
        state = State.DONE;
        return null;
    }

    @CanIgnoreReturnValue
    @Override
    public final boolean hasNext() {
        Preconditions.checkState(state != State.FAILED);
        switch (state) {
            case DONE:
                return false;
            case READY:
                return true;
            default:
        }
        return tryToComputeNext();
    }

    private boolean tryToComputeNext() {
        state = State.FAILED; // temporary pessimism
        next = computeNext();
        if (state != State.DONE) {
            state = State.READY;
            return true;
        }
        return false;
    }

    @CanIgnoreReturnValue
    @Override
    public final T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        state = State.NOT_READY;
        T result = next;
        next = null;
        return result;
    }

    public final T peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return next;
    }
}

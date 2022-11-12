package SetUtils.Base;

import SetUtils.Annotations.Beta;
import SetUtils.Annotations.DoNotMock;
import SetUtils.Annotations.GwtCompatible;
import SetUtils.Annotations.Nullable;

import java.io.Serializable;

@DoNotMock("Use Optional.of(value) or Optional.absent()")
@GwtCompatible(serializable = true)
public abstract class Optional<T> implements Serializable {

    public static <T> Optional<T> of(T reference) {
        return new Present<T>(Preconditions.checkNotNull(reference));
    }


    Optional() {
    }

    public abstract T get();
    public abstract T or(T defaultValue);
    public abstract Optional<T> or(Optional<? extends T> secondChoice);

    @Beta
    public abstract T or(Supplier<? extends T> supplier);

    @Override
    public abstract boolean equals(@Nullable Object object);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    private static final long serialVersionUID = 0;
}

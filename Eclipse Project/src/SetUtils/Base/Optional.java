package SetUtils.Base;

import SetUtils.Annotations.Beta;
import SetUtils.Annotations.DoNotMock;
import SetUtils.Annotations.GwtCompatible;
import SetUtils.Annotations.Nullable;
import SetUtils.Collections.AbstractIterator;

import java.io.Serializable;
import java.util.Iterator;

@DoNotMock("Use Optional.of(value) or Optional.absent()")
@GwtCompatible(serializable = true)
public abstract class Optional<T> implements Serializable {

    public static <T> Optional<T> absent() {
        return Absent.withType();
    }

    public static <T> Optional<T> of(T reference) {
        return new Present<T>(Preconditions.checkNotNull(reference));
    }


    public static <T> java.util.@Nullable Optional<T> toJavaUtil(
            @Nullable Optional<T> googleOptional) {
        return googleOptional == null ? null : googleOptional.toJavaUtil();
    }

    public java.util.Optional<T> toJavaUtil() {
        return java.util.Optional.ofNullable(orNull());
    }

    Optional() {
    }

    public abstract boolean isPresent();
    public abstract T get();
    public abstract T or(T defaultValue);
    public abstract Optional<T> or(Optional<? extends T> secondChoice);

    @Beta
    public abstract T or(Supplier<? extends T> supplier);
    public abstract @Nullable T orNull();

    @Override
    public abstract boolean equals(@Nullable Object object);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    @Beta
    public static <T> Iterable<T> presentInstances(
            final Iterable<? extends Optional<? extends T>> optionals) {
        Preconditions.checkNotNull(optionals);
        return () -> new AbstractIterator<T>() {
            private final Iterator<? extends Optional<? extends T>> iterator =
                    Preconditions.checkNotNull(optionals.iterator());

            @Override
            protected T computeNext() {
                while (iterator.hasNext()) {
                    Optional<? extends T> optional = iterator.next();
                    if (optional.isPresent()) {
                        return optional.get();
                    }
                }
                return endOfData();
            }
        };
    }

    private static final long serialVersionUID = 0;
}

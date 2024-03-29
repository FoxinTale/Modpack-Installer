package SetUtils.Base;

import SetUtils.Annotations.GwtCompatible;
import SetUtils.Annotations.Nullable;

@GwtCompatible
final class Present<T> extends Optional<T> {
    private final T reference;

    Present(T reference) {
        this.reference = reference;
    }

    @Override
    public T get() {
        return reference;
    }

    @Override
    public T or(T defaultValue) {
        Preconditions.checkNotNull(defaultValue, "use Optional.orNull() instead of Optional.or(null)");
        return reference;
    }

    @Override
    public Optional<T> or(Optional<? extends T> secondChoice) {
        Preconditions.checkNotNull(secondChoice);
        return this;
    }

    @Override
    public T or(Supplier<? extends T> supplier) {
        Preconditions.checkNotNull(supplier);
        return reference;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object instanceof Present) {
            Present<?> other = (Present<?>) object;
            return reference.equals(other.reference);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0x598df91c + reference.hashCode();
    }

    @Override
    public String toString() {
        return "Optional.of(" + reference + ")";
    }

    private static final long serialVersionUID = 0;
}

package SetUtils.Base;

import SetUtils.Annotations.GwtCompatible;
import SetUtils.Annotations.Nullable;

@GwtCompatible
final class Absent<T> extends Optional<T> {
  static final Absent<Object> INSTANCE = new Absent<>();

  static <T> Optional<T> withType() {
    return (Optional<T>) INSTANCE;
  }

  private Absent() {}

  @Override
  public boolean isPresent() {
    return false;
  }

  @Override
  public T get() {
    throw new IllegalStateException("Optional.get() cannot be called on an absent value");
  }

  @Override
  public T or(T defaultValue) {
    return Preconditions.checkNotNull(defaultValue, "use Optional.orNull() instead of Optional.or(null)");
  }

  @Override
  public Optional<T> or(Optional<? extends T> secondChoice) {
    return (Optional<T>) Preconditions.checkNotNull(secondChoice);
  }

  @Override
  public T or(Supplier<? extends T> supplier) {
    return Preconditions.checkNotNull(
        supplier.get(), "use Optional.orNull() instead of a Supplier that returns null");
  }

  @Override
  public @Nullable T orNull() {
    return null;
  }

  @Override
  public boolean equals(@Nullable Object object) {
    return object == this;
  }

  @Override
  public int hashCode() {
    return 0x79a31aac;
  }

  @Override
  public String toString() {
    return "Optional.absent()";
  }

  private Object readResolve() {
    return INSTANCE;
  }

  private static final long serialVersionUID = 0;
}

package SetUtils.Base;

import SetUtils.Annotations.CanIgnoreReturnValue;
import SetUtils.Annotations.GwtCompatible;

@GwtCompatible
@FunctionalInterface
public interface Supplier<T> extends java.util.function.Supplier<T> {
    @CanIgnoreReturnValue
    @Override
    T get();
}

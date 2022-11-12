package SetUtils.Base;

import SetUtils.Annotations.CanIgnoreReturnValue;
import SetUtils.Annotations.GwtCompatible;
import SetUtils.Annotations.Nullable;

@FunctionalInterface
@GwtCompatible
public interface Predicate<T> extends java.util.function.Predicate<T> {

    @CanIgnoreReturnValue
    boolean apply(@Nullable T input);

    @Override
    boolean equals(@Nullable Object object);

    @Override
    default boolean test(@Nullable T input) {
        return apply(input);
    }
}

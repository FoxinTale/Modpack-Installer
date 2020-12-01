package SetUtils.Base;

import SetUtils.Annotations.CanIgnoreReturnValue;
import SetUtils.Annotations.GwtCompatible;
import SetUtils.Annotations.Nullable;

@GwtCompatible
@FunctionalInterface
public interface Function<F, T> extends java.util.function.Function<F, T> {
  @Override
  @CanIgnoreReturnValue
  @Nullable
  T apply(@Nullable F input);


  @Override
  boolean equals(@Nullable Object object);
}

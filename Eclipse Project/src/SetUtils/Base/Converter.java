package SetUtils.Base;

import SetUtils.Annotations.*;

import java.io.Serializable;

@GwtCompatible
public abstract class Converter<A, B> implements Function<A, B> {
  private final boolean handleNullAutomatically;

  @LazyInit
  @RetainedWith private transient @Nullable Converter<B, A> reverse;

  protected Converter() {
    this(true);
  }

  Converter(boolean handleNullAutomatically) {
    this.handleNullAutomatically = handleNullAutomatically;
  }

  @ForOverride
  protected abstract B doForward(A a);
  @ForOverride
  protected abstract A doBackward(B b);

  @CanIgnoreReturnValue
  public final @Nullable B convert(@Nullable A a) {
    return correctedDoForward(a);
  }

  @Nullable
  B correctedDoForward(@Nullable A a) {
    if (handleNullAutomatically) {
      // TODO(kevinb): we shouldn't be checking for a null result at runtime. Assert?
      return a == null ? null : Preconditions.checkNotNull(doForward(a));
    } else {
      return doForward(a);
    }
  }

  @Nullable
  A correctedDoBackward(@Nullable B b) {
    if (handleNullAutomatically) {
      // TODO(kevinb): we shouldn't be checking for a null result at runtime. Assert?
      return b == null ? null : Preconditions.checkNotNull(doBackward(b));
    } else {
      return doBackward(b);
    }
  }

  @CanIgnoreReturnValue
  public Converter<B, A> reverse() {
    Converter<B, A> result = reverse;
    return (result == null) ? reverse = new ReverseConverter<>(this) : result;
  }

  private static final class ReverseConverter<A, B> extends Converter<B, A>
      implements Serializable {
    final Converter<A, B> original;

    ReverseConverter(Converter<A, B> original) {
      this.original = original;
    }

    @Override
    protected A doForward(B b) {
      throw new AssertionError();
    }

    @Override
    protected B doBackward(A a) {
      throw new AssertionError();
    }

    @Override
    @Nullable
    A correctedDoForward(@Nullable B b) {
      return original.correctedDoBackward(b);
    }

    @Override
    @Nullable
    B correctedDoBackward(@Nullable A a) {
      return original.correctedDoForward(a);
    }

    @Override
    public Converter<A, B> reverse() {
      return original;
    }

    @Override
    public boolean equals(@Nullable Object object) {
      if (object instanceof ReverseConverter) {
        ReverseConverter<?, ?> that = (ReverseConverter<?, ?>) object;
        return this.original.equals(that.original);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return ~original.hashCode();
    }

    @Override
    public String toString() {
      return original + ".reverse()";
    }

    private static final long serialVersionUID = 0L;
  }

  public final <C> Converter<A, C> andThen(Converter<B, C> secondConverter) {
    return doAndThen(secondConverter);
  }
  <C> Converter<A, C> doAndThen(Converter<B, C> secondConverter) {
    return new ConverterComposition<>(this, Preconditions.checkNotNull(secondConverter));
  }

  private static final class ConverterComposition<A, B, C> extends Converter<A, C>
      implements Serializable {
    final Converter<A, B> first;
    final Converter<B, C> second;

    ConverterComposition(Converter<A, B> first, Converter<B, C> second) {
      this.first = first;
      this.second = second;
    }
    @Override
    protected C doForward(A a) {
      throw new AssertionError();
    }

    @Override
    protected A doBackward(C c) {
      throw new AssertionError();
    }

    @Override
    @Nullable
    C correctedDoForward(@Nullable A a) {
      return second.correctedDoForward(first.correctedDoForward(a));
    }

    @Override
    @Nullable
    A correctedDoBackward(@Nullable C c) {
      return first.correctedDoBackward(second.correctedDoBackward(c));
    }

    @Override
    public boolean equals(@Nullable Object object) {
      if (object instanceof ConverterComposition) {
        ConverterComposition<?, ?, ?> that = (ConverterComposition<?, ?, ?>) object;
        return this.first.equals(that.first) && this.second.equals(that.second);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return 31 * first.hashCode() + second.hashCode();
    }

    @Override
    public String toString() {
      return first + ".andThen(" + second + ")";
    }

    private static final long serialVersionUID = 0L;
  }

  @Deprecated
  @Override
  @CanIgnoreReturnValue
  public final @Nullable B apply(@Nullable A a) {
    return convert(a);
  }

  @Override
  public boolean equals(@Nullable Object object) {
    return super.equals(object);
  }
  public static <A, B> Converter<A, B> from(
      Function<? super A, ? extends B> forwardFunction,
      Function<? super B, ? extends A> backwardFunction) {
    return new FunctionBasedConverter<>(forwardFunction, backwardFunction);
  }

  private static final class FunctionBasedConverter<A, B> extends Converter<A, B>
      implements Serializable {
    private final Function<? super A, ? extends B> forwardFunction;
    private final Function<? super B, ? extends A> backwardFunction;

    private FunctionBasedConverter(
        Function<? super A, ? extends B> forwardFunction,
        Function<? super B, ? extends A> backwardFunction) {
      this.forwardFunction = Preconditions.checkNotNull(forwardFunction);
      this.backwardFunction = Preconditions.checkNotNull(backwardFunction);
    }

    @Override
    protected B doForward(A a) {
      return forwardFunction.apply(a);
    }

    @Override
    protected A doBackward(B b) {
      return backwardFunction.apply(b);
    }

    @Override
    public boolean equals(@Nullable Object object) {
      if (object instanceof FunctionBasedConverter) {
        FunctionBasedConverter<?, ?> that = (FunctionBasedConverter<?, ?>) object;
        return this.forwardFunction.equals(that.forwardFunction)
            && this.backwardFunction.equals(that.backwardFunction);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return forwardFunction.hashCode() * 31 + backwardFunction.hashCode();
    }

    @Override
    public String toString() {
      return "Converter.from(" + forwardFunction + ", " + backwardFunction + ")";
    }
  }


}

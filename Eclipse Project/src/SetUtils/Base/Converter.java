package SetUtils.Base;

import SetUtils.Annotations.*;

import java.io.Serializable;

@GwtCompatible
public abstract class Converter<A, B> implements Function<A, B> {
    private final boolean handleNullAutomatically;

    @LazyInit
    @RetainedWith
    private transient @Nullable Converter<B, A> reverse;

    protected Converter() {
        this(true);
    }

    Converter(boolean handleNullAutomatically) {
        this.handleNullAutomatically = handleNullAutomatically;
    }

    @ForOverride
    protected abstract B doForward(A a);

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

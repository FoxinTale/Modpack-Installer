package FileUtils;

import java.math.BigInteger;
import java.util.Objects;

public class Counters {
    private static class AbstractPathCounters implements PathCounters {
        private final Counter byteCounter;
        private final Counter directoryCounter;
        private final Counter fileCounter;
        protected AbstractPathCounters(final Counter byteCounter, final Counter directoryCounter,
            final Counter fileCounter) {
            this.byteCounter = byteCounter;
            this.directoryCounter = directoryCounter;
            this.fileCounter = fileCounter;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AbstractPathCounters)) {
                return false;
            }
            final AbstractPathCounters other = (AbstractPathCounters) obj;
            return Objects.equals(byteCounter, other.byteCounter)
                && Objects.equals(directoryCounter, other.directoryCounter)
                && Objects.equals(fileCounter, other.fileCounter);
        }

        @Override
        public Counter getByteCounter() {
            return byteCounter;
        }

        @Override
        public Counter getDirectoryCounter() {
            return directoryCounter;
        }

        @Override
        public Counter getFileCounter() {
            return this.fileCounter;
        }

        @Override
        public int hashCode() {
            return Objects.hash(byteCounter, directoryCounter, fileCounter);
        }

        @Override
        public void reset() {
            byteCounter.reset();
            directoryCounter.reset();
            fileCounter.reset();
        }

        @Override
        public String toString() {
            return String.format("%,d files, %,d directories, %,d bytes", fileCounter.get(),
                    directoryCounter.get(), byteCounter.get());
        }
    }

    private static final class BigIntegerCounter implements Counter {
        private BigInteger value = BigInteger.ZERO;

        @Override
        public void add(final long val) {
            value = value.add(BigInteger.valueOf(val));

        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Counter)) {
                return false;
            }
            final Counter other = (Counter) obj;
            return Objects.equals(value, other.getBigInteger());
        }

        @Override
        public long get() {
            return value.longValueExact();
        }

        @Override
        public BigInteger getBigInteger() {
            return value;
        }

        @Override
        public Long getLong() {
            return value.longValueExact();
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public void increment() {
            value = value.add(BigInteger.ONE);
        }

        @Override
        public String toString() {
            return value.toString();
        }

        @Override
        public void reset() {
            value = BigInteger.ZERO;
        }
    }

    public interface Counter {
        void add(long val);
        long get();
        BigInteger getBigInteger();
        Long getLong();
        void increment();
        default void reset() {
        }
    }

    private final static class LongCounter implements Counter {
        private long value;
        @Override
        public void add(final long add) {
            value += add;

        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Counter)) {
                return false;
            }
            final Counter other = (Counter) obj;
            return value == other.get();
        }

        @Override
        public long get() {
            return value;
        }

        @Override
        public BigInteger getBigInteger() {
            return BigInteger.valueOf(value);
        }

        @Override
        public Long getLong() {
            return value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public void increment() {
            value++;
        }

        @Override
        public String toString() {
            return Long.toString(value);
        }

        @Override
        public void reset() {
            value = 0L;
        }
    }

    private final static class LongPathCounters extends AbstractPathCounters {
        protected LongPathCounters() {
            super(Counters.longCounter(), Counters.longCounter(), Counters.longCounter());
        }
    }

    private final static class NoopCounter implements Counter {
        static final NoopCounter INSTANCE = new NoopCounter();

        @Override
        public void add(final long add) {

        }
        @Override
        public long get() {
            return 0;
        }

        @Override
        public BigInteger getBigInteger() {
            return BigInteger.ZERO;
        }

        @Override
        public Long getLong() {
            return 0L;
        }

        @Override
        public void increment() {
        }
    }

    private static final class NoopPathCounters extends AbstractPathCounters {
        static final NoopPathCounters INSTANCE = new NoopPathCounters();
        private NoopPathCounters() {
            super(Counters.noopCounter(), Counters.noopCounter(), Counters.noopCounter());
        }
    }

    public interface PathCounters {
        Counter getByteCounter();
        Counter getDirectoryCounter();
        Counter getFileCounter();
        default void reset() {
        }
    }

    public static Counter bigIntegerCounter() {
        return new BigIntegerCounter();
    }
    public static Counter longCounter() {
        return new LongCounter();
    }
    public static PathCounters longPathCounters() {
        return new LongPathCounters();
    }
    public static Counter noopCounter() {
        return NoopCounter.INSTANCE;
    }
    public static PathCounters noopPathCounters() {
        return NoopPathCounters.INSTANCE;
    }
}

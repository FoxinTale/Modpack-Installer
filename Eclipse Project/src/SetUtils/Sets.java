package SetUtils;


import SetUtils.Annotations.CanIgnoreReturnValue;
import SetUtils.Base.Preconditions;
import SetUtils.Collections.AbstractIterator;
import SetUtils.Collections.UnmodifiableIterator;

import java.util.*;
import java.util.stream.Stream;


public final class Sets {
  private Sets() {}

  @Deprecated
  public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
    return Collections.newSetFromMap(map);
  }

  public abstract static class SetView<E> extends AbstractSet<E> {
    private SetView() {}


    @CanIgnoreReturnValue
    @Deprecated
    @Override
    public final boolean add(E e) {
      throw new UnsupportedOperationException();
    }


    @CanIgnoreReturnValue
    @Deprecated
    @Override
    public final boolean remove(Object object) {
      throw new UnsupportedOperationException();
    }


    @CanIgnoreReturnValue
    @Deprecated
    @Override
    public final boolean addAll(Collection<? extends E> newElements) {
      throw new UnsupportedOperationException();
    }


    @CanIgnoreReturnValue
    @Deprecated
    @Override
    public final boolean removeAll(Collection<?> oldElements) {
      throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    @Override
    public final boolean removeIf(java.util.function.Predicate<? super E> filter) {
      throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    @Override
    public final boolean retainAll(Collection<?> elementsToKeep) {
      throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final void clear() {
      throw new UnsupportedOperationException();
    }

    @Override
    public abstract UnmodifiableIterator<E> iterator();
  }

  public static <E> SetView<E> union(final Set<? extends E> set1, final Set<? extends E> set2) {
    Preconditions.checkNotNull(set1, "set1");
    Preconditions.checkNotNull(set2, "set2");

    return new SetView<E>() {
      @Override
      public int size() {
        int size = set1.size();
        for (E e : set2) {
          if (!set1.contains(e)) {
            size++;
          }
        }
        return size;
      }

      @Override
      public boolean isEmpty() {
        return set1.isEmpty() && set2.isEmpty();
      }

      @Override
      public UnmodifiableIterator<E> iterator() {
        return new AbstractIterator<E>() {
          final Iterator<? extends E> itr1 = set1.iterator();
          final Iterator<? extends E> itr2 = set2.iterator();

          @Override
          protected E computeNext() {
            if (itr1.hasNext()) {
              return itr1.next();
            }
            while (itr2.hasNext()) {
              E e = itr2.next();
              if (!set1.contains(e)) {
                return e;
              }
            }
            return endOfData();
          }
        };
      }

      @Override
      public Stream<E> stream() {
        return Stream.concat(set1.stream(), set2.stream().filter(e -> !set1.contains(e)));
      }

      @Override
      public Stream<E> parallelStream() {
        return stream().parallel();
      }

      @Override
      public boolean contains(Object object) {
        return set1.contains(object) || set2.contains(object);
      }

    };
  }

  public static <E> SetView<E> difference(final Set<E> set1, final Set<?> set2) {
    Preconditions.checkNotNull(set1, "set1");
    Preconditions.checkNotNull(set2, "set2");

    return new SetView<E>() {
      @Override
      public UnmodifiableIterator<E> iterator() {
        return new AbstractIterator<E>() {
          final Iterator<E> itr = set1.iterator();

          @Override
          protected E computeNext() {
            while (itr.hasNext()) {
              E e = itr.next();
              if (!set2.contains(e)) {
                return e;
              }
            }
            return endOfData();
          }
        };
      }

      @Override
      public Stream<E> stream() {
        return set1.stream().filter(e -> !set2.contains(e));
      }

      @Override
      public Stream<E> parallelStream() {
        return set1.parallelStream().filter(e -> !set2.contains(e));
      }

      @Override
      public int size() {
        int size = 0;
        for (E e : set1) {
          if (!set2.contains(e)) {
            size++;
          }
        }
        return size;
      }

      @Override
      public boolean isEmpty() {
        return set2.containsAll(set1);
      }

      @Override
      public boolean contains(Object element) {
        return set1.contains(element) && !set2.contains(element);
      }
    };
  }

  public static <E> SetView<E> symmetricDifference(
      final Set<? extends E> set1, final Set<? extends E> set2) {
    Preconditions.checkNotNull(set1, "set1");
    Preconditions.checkNotNull(set2, "set2");

    return new SetView<E>() {
      @Override
      public UnmodifiableIterator<E> iterator() {
        final Iterator<? extends E> itr1 = set1.iterator();
        final Iterator<? extends E> itr2 = set2.iterator();
        return new AbstractIterator<E>() {
          @Override
          public E computeNext() {
            while (itr1.hasNext()) {
              E elem1 = itr1.next();
              if (!set2.contains(elem1)) {
                return elem1;
              }
            }
            while (itr2.hasNext()) {
              E elem2 = itr2.next();
              if (!set1.contains(elem2)) {
                return elem2;
              }
            }
            return endOfData();
          }
        };
      }

      @Override
      public int size() {
        int size = 0;
        for (E e : set1) {
          if (!set2.contains(e)) {
            size++;
          }
        }
        for (E e : set2) {
          if (!set1.contains(e)) {
            size++;
          }
        }
        return size;
      }

      @Override
      public boolean isEmpty() {
        return set1.equals(set2);
      }

      @Override
      public boolean contains(Object element) {
        return set1.contains(element) ^ set2.contains(element);
      }
    };
  }


}

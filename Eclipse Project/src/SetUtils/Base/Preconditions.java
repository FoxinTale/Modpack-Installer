package SetUtils.Base;

import SetUtils.Annotations.CanIgnoreReturnValue;
import SetUtils.Annotations.GwtCompatible;
import SetUtils.Annotations.NonNull;
import SetUtils.Annotations.Nullable;

@GwtCompatible
public final class Preconditions {
  private Preconditions() {}


  public static void checkArgument(boolean b, @Nullable String errorMessageTemplate, int p1) {
    if (!b) {
      throw new IllegalArgumentException(Strings.lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkState(boolean expression) {
    if (!expression) {
      throw new IllegalStateException();
    }
  }

  public static void checkState(boolean expression, @Nullable Object errorMessage) {
    if (!expression) {
      throw new IllegalStateException(String.valueOf(errorMessage));
    }
  }

  public static void checkState(
      boolean expression,
      @Nullable String errorMessageTemplate,
      @Nullable Object @Nullable ... errorMessageArgs) {
    if (!expression) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, errorMessageArgs));
    }
  }

  public static void checkState(boolean b, @Nullable String errorMessageTemplate, char p1) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkState(boolean b, @Nullable String errorMessageTemplate, int p1) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkState(boolean b, @Nullable String errorMessageTemplate, long p1) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkState(
      boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkState(
      boolean b, @Nullable String errorMessageTemplate, char p1, char p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(boolean b, @Nullable String errorMessageTemplate, char p1, int p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, @Nullable String errorMessageTemplate, char p1, long p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, @Nullable String errorMessageTemplate, char p1, @Nullable Object p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(boolean b, @Nullable String errorMessageTemplate, int p1, char p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(boolean b, @Nullable String errorMessageTemplate, int p1, int p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(boolean b, @Nullable String errorMessageTemplate, int p1, long p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, @Nullable String errorMessageTemplate, int p1, @Nullable Object p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, @Nullable String errorMessageTemplate, long p1, char p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(boolean b, @Nullable String errorMessageTemplate, long p1, int p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, @Nullable String errorMessageTemplate, long p1, long p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, @Nullable String errorMessageTemplate, long p1, @Nullable Object p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, char p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, int p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, long p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, @Nullable Object p2) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b,
      @Nullable String errorMessageTemplate,
      @Nullable Object p1,
      @Nullable Object p2,
      @Nullable Object p3) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2, p3));
    }
  }

  public static void checkState(
      boolean b,
      @Nullable String errorMessageTemplate,
      @Nullable Object p1,
      @Nullable Object p2,
      @Nullable Object p3,
      @Nullable Object p4) {
    if (!b) {
      throw new IllegalStateException(Strings.lenientFormat(errorMessageTemplate, p1, p2, p3, p4));
    }
  }

  @CanIgnoreReturnValue
  public static <T extends @NonNull Object> T checkNotNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    }
    return reference;
  }

  @CanIgnoreReturnValue
  public static <T extends @NonNull Object> T checkNotNull(
      T reference, @Nullable Object errorMessage) {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(errorMessage));
    }
    return reference;
  }

  @CanIgnoreReturnValue
  public static int checkElementIndex(int index, int size) {
    return checkElementIndex(index, size, "index");
  }

  @CanIgnoreReturnValue
  public static int checkElementIndex(int index, int size, @Nullable String desc) {
    // Carefully optimized for execution by hotspot (explanatory comment above)
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
    }
    return index;
  }

  private static String badElementIndex(int index, int size, @Nullable String desc) {
    if (index < 0) {
      return Strings.lenientFormat("%s (%s) must not be negative", desc, index);
    } else if (size < 0) {
      throw new IllegalArgumentException("negative size: " + size);
    } else { // index >= size
      return Strings.lenientFormat("%s (%s) must be less than size (%s)", desc, index, size);
    }
  }

  @CanIgnoreReturnValue
  public static int checkPositionIndex(int index, int size) {
    return checkPositionIndex(index, size, "index");
  }

  @CanIgnoreReturnValue
  public static int checkPositionIndex(int index, int size, @Nullable String desc) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(badPositionIndex(index, size, desc));
    }
    return index;
  }

  private static String badPositionIndex(int index, int size, @Nullable String desc) {
    if (index < 0) {
      return Strings.lenientFormat("%s (%s) must not be negative", desc, index);
    } else if (size < 0) {
      throw new IllegalArgumentException("negative size: " + size);
    } else {
      return Strings.lenientFormat("%s (%s) must not be greater than size (%s)", desc, index, size);
    }
  }

  public static void checkPositionIndexes(int start, int end, int size) {
    if (start < 0 || end < start || end > size) {
      throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
    }
  }

  private static String badPositionIndexes(int start, int end, int size) {
    if (start < 0 || start > size) {
      return badPositionIndex(start, size, "start index");
    }
    if (end < 0 || end > size) {
      return badPositionIndex(end, size, "end index");
    }
    // end < start
    return Strings.lenientFormat("end index (%s) must not be less than start index (%s)", end, start);
  }

  public static void checkArgument(boolean b) {
  }
}

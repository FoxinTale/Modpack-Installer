package SetUtils.Annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@SubtypeOf(Nullable.class)
@MonotonicQualifier(NonNull.class)
public @interface MonotonicNonNull {}

package SetUtils.Annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@SubtypeOf({})
@QualifierForLiterals(LiteralKind.NULL)
@DefaultFor(types = Void.class)
public @interface Nullable {}

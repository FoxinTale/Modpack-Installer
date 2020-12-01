package SetUtils.Annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@SubtypeOf(UnknownKeyFor.class)
public @interface KeyFor {
    @JavaExpression
    public String[] value();
}

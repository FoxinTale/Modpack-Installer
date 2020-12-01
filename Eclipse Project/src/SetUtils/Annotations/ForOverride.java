package SetUtils.Annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;
import static javax.lang.model.element.Modifier.*;

@Documented
@IncompatibleModifiers({PUBLIC, PRIVATE, STATIC, FINAL})
@Retention(CLASS)
@Target(METHOD)
public @interface ForOverride {}

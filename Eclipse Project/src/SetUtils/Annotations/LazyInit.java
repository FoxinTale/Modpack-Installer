package SetUtils.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static javax.lang.model.element.Modifier.FINAL;

@IncompatibleModifiers({FINAL})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LazyInit {}

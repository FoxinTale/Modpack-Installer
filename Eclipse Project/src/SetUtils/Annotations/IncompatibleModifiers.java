package SetUtils.Annotations;

import javax.lang.model.element.Modifier;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.ANNOTATION_TYPE)
public @interface IncompatibleModifiers {
  Modifier[] value();
}

package SetUtils.Annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
@AnnotatedFor("nullness")
public @interface PolymorphicQualifier {
    Class<? extends Annotation> value() default Annotation.class;
}

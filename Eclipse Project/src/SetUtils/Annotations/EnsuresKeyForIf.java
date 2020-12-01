package SetUtils.Annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@ConditionalPostconditionAnnotation(qualifier = KeyFor.class)
@InheritedAnnotation
@Repeatable(EnsuresKeyForIf.List.class)
public @interface EnsuresKeyForIf {
    boolean result();

    String[] expression();

    @JavaExpression
    @QualifierArgument("value")
    String[] map();
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
    @ConditionalPostconditionAnnotation(qualifier = KeyFor.class)
    @InheritedAnnotation
    @interface List {
        EnsuresKeyForIf[] value();
    }
}

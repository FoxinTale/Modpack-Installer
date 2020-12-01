package SetUtils.Annotations;

import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@SubtypeOf(MonotonicNonNull.class)
@QualifierForLiterals(LiteralKind.STRING)
@DefaultQualifierInHierarchy
@DefaultFor(TypeUseLocation.EXCEPTION_PARAMETER)
@UpperBoundFor(
        typeKinds = {
            TypeKind.PACKAGE,
            TypeKind.INT,
            TypeKind.BOOLEAN,
            TypeKind.CHAR,
            TypeKind.DOUBLE,
            TypeKind.FLOAT,
            TypeKind.LONG,
            TypeKind.SHORT,
            TypeKind.BYTE
        })
public @interface NonNull {}

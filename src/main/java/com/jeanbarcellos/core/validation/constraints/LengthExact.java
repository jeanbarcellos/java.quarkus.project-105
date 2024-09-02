package com.jeanbarcellos.core.validation.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.jeanbarcellos.core.validation.constraints.LengthExact.List;
import com.jeanbarcellos.core.validation.validators.LengthExactValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Anotação para validar se o tamanho da string é exatamente o número
 * informnado.
 *
 * @author Jean Barcellos (jeanbarcellos@hotmail.com)
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = LengthExactValidator.class)
@Repeatable(List.class)
@Documented
public @interface LengthExact {

    String message() default "{validation.annotation.LengthExact.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int length();

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        LengthExact[] value();
    }

}

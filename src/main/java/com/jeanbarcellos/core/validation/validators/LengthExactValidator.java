package com.jeanbarcellos.core.validation.validators;

import org.apache.commons.lang3.StringUtils;

import com.jeanbarcellos.core.validation.constraints.LengthExact;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para verificar se a string informada contem exatamento um tamanho
 *
 * @author Jean Barcellos (jeanbarcellos@hotmail.com)
 */
public class LengthExactValidator implements ConstraintValidator<LengthExact, String> {

    private int length;

    @Override
    public void initialize(LengthExact parameters) {
        this.length = parameters.length();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        return value.length() == length;
    }

}

package com.jeanbarcellos.core.validation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.jeanbarcellos.core.Constants;
import com.jeanbarcellos.core.exception.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;

@ApplicationScoped
public class Validator {

    public static final String MSG_ERROR = "O campo '%s' %s";

    @Inject
    jakarta.validation.Validator innerValidator;

    public <T> Set<ConstraintViolation<T>> getViolations(T model) {
        return this.innerValidator.validate(model);
    }

    public <T> void validate(T model) {
        var constraints = this.getViolations(model);

        if (!constraints.isEmpty()) {
            throw createValidationException(constraints);
        }
    }

    private static <T> ValidationException createValidationException(Set<ConstraintViolation<T>> constraints) {
        return ValidationException.of(Constants.MSG_ERROR_VALIDATION, createMessages(constraints));
    }

    private static <T> List<String> createMessages(Set<ConstraintViolation<T>> constraints) {
        return constraints.stream()
                .map(constraint -> String.format(MSG_ERROR,
                        constraint.getPropertyPath().toString(), constraint.getMessage()))
                .collect(Collectors.toList());
    }

}

package com.jeanbarcellos.core.validation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.jeanbarcellos.core.Constants;
import com.jeanbarcellos.core.exception.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class Validator {

    public static final String MSG_ERROR = "O campo '%s' %s";

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

    public static <T> ValidationException createValidationException(Set<ConstraintViolation<T>> constraints) {
        return ValidationException.of(Constants.MSG_ERROR_VALIDATION, createMessages(constraints));
    }

    public static <T> List<String> createMessages(Set<ConstraintViolation<T>> constraints) {
        return constraints.stream()
                .map(Validator::createMessage)
                .collect(Collectors.toList());
    }

    public static <T> String createMessage(ConstraintViolation<T> constraint) {
        return String.format(MSG_ERROR,
                constraint.getPropertyPath().toString(),
                constraint.getMessage());
    }

    public static String createMessage(String propertyPath, String message) {
        return String.format(MSG_ERROR, propertyPath, message);
    }

}

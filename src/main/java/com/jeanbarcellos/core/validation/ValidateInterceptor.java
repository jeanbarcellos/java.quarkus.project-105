package com.jeanbarcellos.core.validation;

import java.lang.annotation.Annotation;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Dependent
@Validate
@Priority(0)
@Interceptor
public class ValidateInterceptor {

    @Inject
    protected Validator validator;

    @AroundInvoke
    Object validate(InvocationContext context) throws Exception {

        var model = this.getModel(context);

        if (model != null) {
            this.validator.validate(model);
        }

        return context.proceed();
    }

    private Object getModel(InvocationContext context) {
        Object[] parameterValues = context.getParameters();
        Annotation[][] parameterAnnotations = context.getMethod().getParameterAnnotations();

        int i = 0;
        for (Annotation[] annotations : parameterAnnotations) {
            var value = parameterValues[i];

            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == Validate.class) {
                    return value;
                }
            }
            i++;
        }

        return null;
    }

}

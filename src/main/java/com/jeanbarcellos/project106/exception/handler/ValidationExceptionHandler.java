package com.jeanbarcellos.project106.exception.handler;

import com.jeanbarcellos.core.dto.ErrorResponse;
import com.jeanbarcellos.core.exception.ValidationException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        log.error(exception.getMessageToLog());

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(ErrorResponse.of(exception.getMessage(), exception.getErrors()))
                .build();
    }

}

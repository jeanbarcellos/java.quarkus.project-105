package com.jeanbarcellos.core.serialization;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomLocalDateDeserializer extends StdDeserializer<LocalDate> {

    protected CustomLocalDateDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    private static final long serialVersionUID = 1L;

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        return LocalDate.parse(jp.readValueAs(String.class));
    }

}

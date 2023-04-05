package com.example.oauthlogin.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class CustomDateTimeSerializer extends JsonSerializer<DateTime> {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @Override
    public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        jgen.writeString(formatter.format(value.toDate()));
    }
}

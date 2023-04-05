package com.example.oauthlogin.errors.handler;

import com.example.oauthlogin.util.CustomDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {

    private HttpStatus status;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private DateTime timestamp;

    private String reason;

    private String exception;

    public ApiError(HttpStatus status) {
        this.timestamp = DateTime.now();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this.timestamp = DateTime.now();
        this.status = status;
        this.reason = "Unexpected error";
    }

    public ApiError(HttpStatus status, String reason, String exception, Throwable ex) {
        this.timestamp = DateTime.now();
        this.status = status;
        this.reason = reason;
        this.exception = exception;
    }
}

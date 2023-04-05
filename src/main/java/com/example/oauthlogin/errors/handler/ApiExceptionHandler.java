package com.example.oauthlogin.errors.handler;

import com.example.oauthlogin.errors.UserAlreadyExistsException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.sql.SQLException;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return buildResponseEntity(new ApiError(BAD_REQUEST, ex.getMessage(), ex.getClass().getSimpleName(), ex));
    }


    @Override
    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, Object body, @NonNull HttpHeaders headers, @NonNull HttpStatusCode statusCode, @NonNull WebRequest request) {
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, ex.getMessage(), ex.getClass().getSimpleName(), ex));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(@NonNull HttpRequestMethodNotSupportedException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return buildResponseEntity(new ApiError(METHOD_NOT_ALLOWED, ex.getMessage(), ex.getClass().getSimpleName(), ex));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(@NonNull MissingServletRequestParameterException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return buildResponseEntity(new ApiError(BAD_REQUEST, ex.getMessage(), ex.getClass().getSimpleName(), ex));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(@NonNull NoHandlerFoundException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return buildResponseEntity(new ApiError(NOT_FOUND, ex.getMessage(), ex.getClass().getSimpleName(), ex));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(@NonNull HttpMediaTypeNotSupportedException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return buildResponseEntity(new ApiError(UNSUPPORTED_MEDIA_TYPE, ex.getMessage(), ex.getClass().getSimpleName(), ex));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(@NonNull TypeMismatchException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return buildResponseEntity(new ApiError(BAD_REQUEST, ex.getMessage(), ex.getClass().getSimpleName(), ex));
    }

    @ExceptionHandler({SQLException.class})
    protected ResponseEntity<Object> handleSqlException(SQLException ex) {
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, ex.getMessage(), ex.getClass().getSimpleName(), ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleEntityNotFound(IllegalArgumentException ex) {
        ApiError errorHandler = new ApiError(BAD_REQUEST, ex.getMessage(), ex.getClass().getSimpleName(), ex);
        return buildResponseEntity(errorHandler);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    protected ResponseEntity<Object> handleEntityNotFound(UserAlreadyExistsException ex) {
        ApiError errorHandler = new ApiError(BAD_REQUEST, ex.getMessage(), ex.getClass().getSimpleName(), ex);
        return buildResponseEntity(errorHandler);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
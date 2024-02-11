package com.adityasamant.learnings.restclient.errors;

import com.adityasamant.learnings.restclient.exceptions.CustomerServiceAuthorizationException;
import com.adityasamant.learnings.restclient.exceptions.CustomerServiceConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
class CustomControllerAdvice {

    // Handle a CustomerServiceConnectionException
    @ExceptionHandler(CustomerServiceConnectionException.class)
    public ResponseEntity<ErrorResponse> handleCustomerServiceConnectionException(Exception e) {
        // converting the stack trace to String
        return getResponseEntity(e, HttpStatus.BAD_GATEWAY); // 502
    }

    // Handle a CustomerServiceConnectionException
    @ExceptionHandler(CustomerServiceAuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleCustomerServiceAuthorizationException(Exception e) {
        // converting the stack trace to String
        return getResponseEntity(e, HttpStatus.FORBIDDEN); // 403
    }

    // fallback method
    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorResponse> handleExceptions(Exception e) {
        // converting the stack trace to String
        return getResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    private static ResponseEntity<ErrorResponse> getResponseEntity(Exception e, HttpStatus status) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage(), stackTrace), status);
        // assuming to be in staging environment, otherwise stackTrace should not be displayed
    }
}

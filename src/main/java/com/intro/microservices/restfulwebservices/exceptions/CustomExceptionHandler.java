package com.intro.microservices.restfulwebservices.exceptions;

import com.intro.microservices.restfulwebservices.dto.ExceptionFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class is a centralized exception handling across all the api in this project.
 * If any of the known exception are thrown by the API, error message will be formatted as expected below.
 */
@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnableToProcessException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UnableToProcessException ex, WebRequest request) {
        ExceptionFormat format = new ExceptionFormat(ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(format, HttpStatus.NOT_FOUND);
    }
}

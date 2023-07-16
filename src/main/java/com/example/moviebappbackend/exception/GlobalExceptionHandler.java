package com.example.moviebappbackend.exception;

import jakarta.persistence.EntityNotFoundException;
import org.postgresql.util.PSQLException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler   {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", new Date(System.currentTimeMillis()));
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        responseBody.put("errors", errors);

        return responseBody;
    }
    @ExceptionHandler(ScreeningFullException.class)
    public ResponseEntity<Object> handleScreeningFullException(ScreeningFullException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsernamePasswordException.class)
    public ResponseEntity<Object> usernamePasswordErrorException(UsernamePasswordException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse("You are not authorized to access this resource",HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<Object> handlePSQLException(PSQLException ex) {
        String errorMessage = ex.getMessage();

        // Check if the error message contains the "duplicate key value violates unique constraint" string
        if (errorMessage.contains("duplicate key value violates unique constraint")) {
            String message = "Duplicate entry. The resource you are trying to create already exists.";
            HttpStatus status = HttpStatus.CONFLICT; // You can choose the appropriate HTTP status code.

            ExceptionResponse errorResponse = new ExceptionResponse(message, HttpStatus.CONFLICT);
            return new ResponseEntity<>(errorResponse, status);
        }

        // For other cases of PSQLException, we can add additional handling here if needed.

        // Return a generic error response for unhandled cases
        String genericMessage = "An error occurred while processing your request.";
        HttpStatus genericStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse genericErrorResponse = new ExceptionResponse(genericMessage,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(genericErrorResponse, genericStatus);
    }
    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<Object> handleUnauthorizedUserException(UnauthorizedUserException ex) {
        String message = ex.getMessage();
        ExceptionResponse genericErrorResponse = new ExceptionResponse(message,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(genericErrorResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        String message = ex.getMessage();
        ExceptionResponse errorResponse = new ExceptionResponse(message, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}


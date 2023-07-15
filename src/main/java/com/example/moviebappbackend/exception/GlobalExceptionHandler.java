package com.example.moviebappbackend.exception;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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
}


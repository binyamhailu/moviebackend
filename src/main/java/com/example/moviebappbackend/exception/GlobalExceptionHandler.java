package com.example.moviebappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<Object> handleUnauthorizedUserException(UnauthorizedUserException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
}


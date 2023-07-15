package com.example.moviebappbackend.exception;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {
    private final String message;
    private final HttpStatus status;

    public ExceptionResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}


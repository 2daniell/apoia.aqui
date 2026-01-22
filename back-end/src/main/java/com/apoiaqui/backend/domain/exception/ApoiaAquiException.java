package com.apoiaqui.backend.domain.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ApoiaAquiException extends RuntimeException {

    public record ExceptionResponse(
        int status,
        String message,
        String path,
        LocalDateTime timestamp
    ) {}

    private int httpStatusCode;
    private String message;
    
    public ExceptionResponse toResponse(String path) {
        return new ExceptionResponse(httpStatusCode, message, path, LocalDateTime.now());
    }
}

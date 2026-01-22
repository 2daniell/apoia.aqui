package com.apoiaqui.backend.domain.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.apoiaqui.backend.domain.exception.InvalidResourceException;
import com.apoiaqui.backend.domain.exception.NotFoundException;
import com.apoiaqui.backend.domain.exception.UnauthorizedException;
import com.apoiaqui.backend.domain.exception.ApoiaAquiException.ExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(NotFoundException exception, HttpServletRequest request) {
        ExceptionResponse response = exception.toResponse(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.valueOf(response.status())).body(response);
    }

    @ExceptionHandler(InvalidResourceException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequest(InvalidResourceException exception, HttpServletRequest request) {
        ExceptionResponse response = exception.toResponse(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.valueOf(response.status())).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorized(UnauthorizedException exception, HttpServletRequest request) {
        ExceptionResponse response = exception.toResponse(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.valueOf(response.status())).body(response);
    }
    
}

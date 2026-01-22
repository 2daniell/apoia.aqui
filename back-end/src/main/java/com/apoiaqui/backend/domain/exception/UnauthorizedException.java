package com.apoiaqui.backend.domain.exception;

public class UnauthorizedException extends ApoiaAquiException {

    private static final int DEFAULT_HTTP_STATUS = 401;

    public UnauthorizedException(String message) {
        super(DEFAULT_HTTP_STATUS, message);
    }
    
}

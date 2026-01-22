package com.apoiaqui.backend.domain.exception;

public class NotFoundException extends ApoiaAquiException {

    public static final int DEFAULT_HTTP_STATUS = 404;

    public NotFoundException(String message) {
        super(DEFAULT_HTTP_STATUS, message);
    }
    
}

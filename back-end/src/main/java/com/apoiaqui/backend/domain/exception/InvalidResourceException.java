package com.apoiaqui.backend.domain.exception;

public class InvalidResourceException extends ApoiaAquiException {

    public static int DEFAULT_HTTP_STATUS = 400;

    public InvalidResourceException(String message) {
        super(DEFAULT_HTTP_STATUS, message);
    }
    
}

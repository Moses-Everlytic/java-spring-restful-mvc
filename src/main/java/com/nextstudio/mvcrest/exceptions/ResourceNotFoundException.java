package com.nextstudio.mvcrest.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public ResourceNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ResourceNotFoundException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }
}

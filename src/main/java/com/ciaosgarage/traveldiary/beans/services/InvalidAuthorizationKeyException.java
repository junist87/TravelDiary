package com.ciaosgarage.traveldiary.beans.services;

public class InvalidAuthorizationKeyException extends RuntimeException {
    public InvalidAuthorizationKeyException() {
        super();
    }

    public InvalidAuthorizationKeyException(String message) {
        super(message);
    }

    public InvalidAuthorizationKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAuthorizationKeyException(Throwable cause) {
        super(cause);
    }

    protected InvalidAuthorizationKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.ciaosgarage.traveldiary.beans.dao.exceptions;

public class InvalidVoObjectException extends RuntimeException {
    public InvalidVoObjectException() {
        super();
    }

    public InvalidVoObjectException(String message) {
        super(message);
    }

    public InvalidVoObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVoObjectException(Throwable cause) {
        super(cause);
    }

    protected InvalidVoObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

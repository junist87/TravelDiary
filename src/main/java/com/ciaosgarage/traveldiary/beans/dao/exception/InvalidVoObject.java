package com.ciaosgarage.traveldiary.beans.dao.exception;

public class InvalidVoObject extends RuntimeException {
    public InvalidVoObject() {
        super();
    }

    public InvalidVoObject(String message) {
        super(message);
    }

    public InvalidVoObject(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVoObject(Throwable cause) {
        super(cause);
    }

    protected InvalidVoObject(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

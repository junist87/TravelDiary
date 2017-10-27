package com.ciaosgarage.traveldiary.beans.dao.exceptions;

public class CannotAccessFieldValueException extends RuntimeException {
    public CannotAccessFieldValueException() {
        super();
    }

    public CannotAccessFieldValueException(String message) {
        super(message);
    }

    public CannotAccessFieldValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotAccessFieldValueException(Throwable cause) {
        super(cause);
    }

    protected CannotAccessFieldValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

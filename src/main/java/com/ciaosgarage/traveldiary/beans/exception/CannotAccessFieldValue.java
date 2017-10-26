package com.ciaosgarage.traveldiary.beans.exception;

public class CannotAccessFieldValue extends RuntimeException {
    public CannotAccessFieldValue() {
        super();
    }

    public CannotAccessFieldValue(String message) {
        super(message);
    }

    public CannotAccessFieldValue(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotAccessFieldValue(Throwable cause) {
        super(cause);
    }

    protected CannotAccessFieldValue(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

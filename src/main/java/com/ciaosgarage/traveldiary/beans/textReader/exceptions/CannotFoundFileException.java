package com.ciaosgarage.traveldiary.beans.textReader.exceptions;

public class CannotFoundFileException extends RuntimeException {
    public CannotFoundFileException() {
        super();
    }

    public CannotFoundFileException(String message) {
        super(message);
    }

    public CannotFoundFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotFoundFileException(Throwable cause) {
        super(cause);
    }

    protected CannotFoundFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

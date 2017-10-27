package com.ciaosgarage.traveldiary.beans.mailSender.exceptions;

public class CannotSendEmailException extends RuntimeException {
    public CannotSendEmailException() {
        super();
    }

    public CannotSendEmailException(String message) {
        super(message);
    }

    public CannotSendEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotSendEmailException(Throwable cause) {
        super(cause);
    }

    protected CannotSendEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

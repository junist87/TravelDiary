package com.ciaosgarage.traveldiary.beans.dao.exception;

public class CannotDefineDataType extends RuntimeException {
    public CannotDefineDataType() {
        super();
    }

    public CannotDefineDataType(String message) {
        super(message);
    }

    public CannotDefineDataType(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotDefineDataType(Throwable cause) {
        super(cause);
    }

    protected CannotDefineDataType(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

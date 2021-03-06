package com.ciaosgarage.traveldiary.beans.dao.exceptions;

public class NoExistPrimaryKeyField extends RuntimeException {

    public NoExistPrimaryKeyField() {
        super();
    }

    public NoExistPrimaryKeyField(String message) {
        super(message);
    }

    public NoExistPrimaryKeyField(String message, Throwable cause) {
        super(message, cause);
    }

    public NoExistPrimaryKeyField(Throwable cause) {
        super(cause);
    }

    protected NoExistPrimaryKeyField(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.ciaosgarage.traveldiary.domain.account;

public enum AccountAutholization {
    LOCK(0), UNLOCK(1);

    Integer intValue;

    AccountAutholization(Integer intValue) {
        this.intValue = intValue;
    }

    static public int getIntValue(AccountAutholization value) {
        switch (value) {
            case LOCK:
                return LOCK.intValue;
            case UNLOCK:
                return UNLOCK.intValue;
            default:
                throw new RuntimeException("!- Not exist value : " + value);
        }
    }

    static public AccountAutholization getAccountAutholizationValue(int value) {
        switch (value) {
            case 0:
                return LOCK;
            case 1:
                return UNLOCK;
            default:
                throw new RuntimeException("!- Not exist value : " + value);
        }
    }
}

package com.ciaosgarage.traveldiary.domain.seqTable;

public enum SeqTableStatus {
    WORKING(1), DELETED(2);

    private int intValue;

    SeqTableStatus(int intValue) {
        this.intValue = intValue;
    }

    public static int getIntValue(SeqTableStatus status) {
        switch (status) {
            case WORKING: return  1;
            case DELETED: return  2;
            default: return 0;
        }
    }

    public static SeqTableStatus getSeqTableStatus(int value) {
        switch (value) {
            case 1 : return WORKING;
            case 2 : return DELETED;
            default: return null;
        }
    }
}

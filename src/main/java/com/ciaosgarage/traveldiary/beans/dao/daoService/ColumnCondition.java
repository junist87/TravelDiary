package com.ciaosgarage.traveldiary.beans.dao.daoService;

public class ColumnCondition {
    String columnName;
    Object key;

    public ColumnCondition(String columnName, Object key) {
        this.columnName = columnName;
        this.key = key;
    }

    public String getColumnName() {
        return columnName;
    }

    public Object getKey() {
        return key;
    }
}

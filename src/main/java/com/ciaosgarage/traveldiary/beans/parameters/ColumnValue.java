package com.ciaosgarage.traveldiary.beans.parameters;

public class ColumnValue {
    String columnName;
    Object value;

    public ColumnValue(String columnName, Object value) {
        this.columnName = columnName;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public Object getValue() {
        return value;
    }
}

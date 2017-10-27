package com.ciaosgarage.traveldiary.beans.dao.parameters;

public class ColumnValue {
    String columnName;
    String mapperName;
    Object value;

    public ColumnValue(String columnName, Object value) {
        this.columnName = columnName;
        this.mapperName = columnName;
        this.value = value;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }

    public String getMapperName() {
        return mapperName;
    }

    public String getColumnName() {
        return columnName;
    }

    public Object getValue() {
        return value;
    }
}

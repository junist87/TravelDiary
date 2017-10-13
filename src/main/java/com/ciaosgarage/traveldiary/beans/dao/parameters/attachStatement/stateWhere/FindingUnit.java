package com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.stateWhere;

public class FindingUnit {
    private String columnName;
    private Object key;

    public FindingUnit(String columnName, Object key) {
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

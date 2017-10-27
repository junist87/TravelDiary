package com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.stateWhere;


import com.ciaosgarage.traveldiary.beans.dao.enums.DatabaseType;
import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;

import java.util.ArrayList;
import java.util.List;

public class StateWhere implements AttachStatement {

    private SWOperator operator = SWOperator.EQUAL;
    private SWComparator comparator = SWComparator.AND;
    private List<ColumnValue> columnValues;

    public StateWhere(List<ColumnValue> columnValues) {
        this.columnValues = columnValues;
    }

    public StateWhere(ColumnValue... columnValues) {
        this.columnValues = new ArrayList<>();
        for (ColumnValue value : columnValues) {
            this.columnValues.add(value);
        }
    }

    public void setOperator(SWOperator operator) {
        this.operator = operator;
    }

    public void setComparator(SWComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public String getStatement() {
        columnValues = insertMapperName(columnValues);
        StringBuffer sql = new StringBuffer("WHERE ");
        for (ColumnValue value : columnValues) {
            sql.append(value.getColumnName() + " ");
            sql.append(this.getOperator() + " ");
            sql.append(":" + value.getMapperName());
            sql.append(this.getComparator());
        }

        return sql.substring(0, sql.length() - this.getComparator().length());
    }

    private List<ColumnValue> insertMapperName(List<ColumnValue> list) {
        int index = 0;
        for (ColumnValue value : list) {
            value.setMapperName(String.valueOf(this.hashCode()) + String.valueOf(index));
            index += 1;
        }
        return list;
    }

    @Override
    public List<ColumnValue> getMapperList() {
        columnValues = insertMapperName(columnValues);
        return columnValues;
    }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.UNIVERSIAL;
    }

    private String getOperator() {
        switch (operator) {
            case EQUAL:
                return "=";
            case LIKE:
                return "LIKE";
            default:
                return null;
        }
    }

    private String getComparator() {
        switch (comparator) {
            case OR:
                return " OR ";
            case AND:
                return " AND ";
            default:
                return null;
        }
    }
}

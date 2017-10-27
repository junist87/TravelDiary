package com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.stateOrderBy;


import com.ciaosgarage.traveldiary.beans.dao.enums.DatabaseType;
import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;

import java.util.ArrayList;
import java.util.List;

public class StateOrderBy implements AttachStatement {
    private SOBOperator operator;
    private String columnName;

    public StateOrderBy(SOBOperator operator, String columnName) {
        this.operator = operator;
        this.columnName = columnName;

    }

    public StateOrderBy(SOBOperator operator) {
        this.operator = operator;
    }

    @Override
    public String getStatement() {
        String sql;
        if (columnName != null) {
            sql = "ORDER BY " + columnName + " " + getOperator();
        } else {
            sql = "ORDER BY " + getOperator();
        }

        return sql;
    }

    private String getOperator() {
        switch (operator) {
            case ASC:
                return "ASC";
            case DESC:
                return "DESC";
            default:
                return null;
        }
    }


    @Override
    public List<ColumnValue> getMapperList() {
        return new ArrayList<>();
    }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.UNIVERSIAL;
    }
}

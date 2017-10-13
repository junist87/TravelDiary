package com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.stateWhere;

import com.ciaosgarage.traveldiary.beans.dao.parameters.DatabaseType;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateWhere implements AttachStatement {

    private SWComparator comparator = SWComparator.EQUAL;
    private SWValuesOperator operator = SWValuesOperator.AND;
    private List<FindingUnit> unitList = new ArrayList<>();
    private String columnName;

    public StateWhere(String columnName, Object key) {
        this.columnName = columnName;
        unitList.add(new FindingUnit(columnName, key));
    }

    public StateWhere(List<FindingUnit> units) {
        unitList = units;
    }

    public StateWhere() {
    }

    public void addUnit(FindingUnit unit) {
        unitList.add(unit);
    }

    public void setComparator(SWComparator comparator) {
        this.comparator = comparator;
    }

    public void setOperator(SWValuesOperator operator) {
        this.operator = operator;
    }


    @Override
    public String getStatement() {
        StringBuffer sql = new StringBuffer("WHERE ");
        int count = 0;
        for (FindingUnit unit : unitList) {
            sql.append(unit.getColumnName() + getComparator());
            sql.append(":stateWhere" + unit.getColumnName() + String.valueOf(count));
            sql.append(getOperator());
            count += 1;
        }
        return sql.substring(0, sql.length() - 5);
    }

    @Override
    public Map<String, Object> getMapper() {
        Map<String, Object> map = new HashMap<>();
        int count = 0;
        for (FindingUnit unit : unitList) {
            map.put("stateWhere" + unit.getColumnName() + String.valueOf(count), unit.getKey());
            count += 1;
        }
        return map;
    }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.UNIVERSIAL;
    }

    private String getComparator() {
        switch (comparator) {
            case EQUAL:
                return "=";
            case LIKE:
                return "LIKE";
            default:
                return null;
        }
    }

    private String getOperator() {
        switch (operator) {
            case OR:
                return " OR  ";
            case AND:
                return " AND ";
            default:
                return null;
        }
    }
}

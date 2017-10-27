package com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.stateLimit;


import com.ciaosgarage.traveldiary.beans.dao.enums.DatabaseType;
import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;

import java.util.ArrayList;
import java.util.List;

public class StateLimit implements AttachStatement {
    private int startIndex;
    private int unit;

    private String startIndexTag = "StateLimitStartIndex";
    private String unitTag = "StateLimitUnitTag";

    public StateLimit(int unit) {
        this.unit = unit;
        this.startIndex = 0;
    }

    public StateLimit(int startIndex, int unit) {
        this.startIndex = startIndex;
        this.unit = unit;
    }


    @Override
    public String getStatement() {
        String sql;
        if (startIndex == 0) {
            sql = "LIMIT :" + unitTag;
        } else {
            sql = "LIMIT :" + startIndexTag + ", :" + unitTag;
        }
        return sql;

    }

    @Override
    public List<ColumnValue> getMapperList() {
        List<ColumnValue> mapper = new ArrayList<>();

        ColumnValue columnValue = new ColumnValue(null, startIndex);
        columnValue.setMapperName(startIndexTag);
        mapper.add(columnValue);

        columnValue = new ColumnValue(null, unit);
        columnValue.setMapperName(unitTag);
        mapper.add(columnValue);
        return mapper;
    }


    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }
}

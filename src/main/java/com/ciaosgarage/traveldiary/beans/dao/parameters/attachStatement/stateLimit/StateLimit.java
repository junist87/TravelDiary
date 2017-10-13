package com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.stateLimit;

import com.ciaosgarage.traveldiary.beans.dao.parameters.DatabaseType;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;

import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Object> getMapper() {
        Map<String, Object> mapper = new HashMap<>();
        mapper.put(startIndexTag, startIndex);
        mapper.put(unitTag, unit);
        return mapper;
    }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }
}

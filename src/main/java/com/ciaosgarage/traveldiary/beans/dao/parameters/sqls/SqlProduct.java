package com.ciaosgarage.traveldiary.beans.dao.parameters.sqls;

import java.util.Map;

public class SqlProduct {
    private String sql;
    private Map<String, Object> mapper;

    public SqlProduct(String sql, Map<String, Object> mapper) {
        this.sql = sql;
        this.mapper = mapper;
    }

    public String getSql() {
        return sql;
    }

    public Map<String, Object> getMapper() {
        return mapper;
    }
}

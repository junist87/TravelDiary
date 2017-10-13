package com.ciaosgarage.traveldiary.beans.dao.sqlStorage;

import com.ciaosgarage.traveldiary.beans.dao.parameters.sqls.SqlIndex;
import com.ciaosgarage.traveldiary.beans.dao.parameters.sqls.SqlType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BasicSqlStorage implements SqlStorage {
    Map<Class, Map<SqlType, String>> sqlStorage;

    public BasicSqlStorage() {
        sqlStorage = new HashMap<>();
    }

    @Override
    public String getSql(SqlIndex index) {
        Map<SqlType, String> mapClass = sqlStorage.get(index.getVoInfo());
        if (mapClass == null) return null;
        return mapClass.get(index.getSqlType());
    }

    @Override
    public void setSql(SqlIndex index, String sql) {
        Map<SqlType, String> targetMap = sqlStorage.get(index.getVoInfo());
        if (targetMap == null) {
            targetMap = new HashMap<>();
            targetMap.put(index.getSqlType(), sql);
            sqlStorage.put(index.getVoInfo(), targetMap);
        } else {
            targetMap.put(index.getSqlType(), sql);
        }

    }
}

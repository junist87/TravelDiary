package com.ciaosgarage.traveldiary.beans.dao.sqlStorage;

import com.ciaosgarage.traveldiary.beans.dao.parameters.SqlIndex;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SqlStorageImpl implements SqlStorage {
    Map<SqlIndex, String> storage;

    public SqlStorageImpl() {
        storage = new HashMap<>();
    }

    @Override
    public String getSql(SqlIndex index) {
        return storage.get(index);
    }

    @Override
    public void setSql(SqlIndex index, String sql) {
        storage.put(index, sql);
    }
}

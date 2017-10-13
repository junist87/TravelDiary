package com.ciaosgarage.traveldiary.beans.dao.daoSettings;

import com.ciaosgarage.traveldiary.beans.dao.parameters.DatabaseType;
import org.springframework.stereotype.Repository;

@Repository
public class DaoSettings {
    // 기본값이 존재한다
    public String tablePrefix = "tbl";
    public DatabaseType databaseType = DatabaseType.MYSQL;

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }
}

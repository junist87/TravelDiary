package com.ciaosgarage.traveldiary.beans.daoSettings;

import com.ciaosgarage.traveldiary.beans.enums.DatabaseType;
import org.springframework.stereotype.Repository;

@Repository
public class DaoSettings {

    // 기본값이 존재한다
    public String tablePrefix = "tbl";

    // 데이터베이스 종류
    public DatabaseType databaseType = DatabaseType.MYSQL;

    // 암호화의 기본키
    public String cryptionKey = "AEdfezo3mz12ngod1pdmviajdfe";

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }
}

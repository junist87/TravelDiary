package com.ciaosgarage.traveldiary.beans.parameters;

import com.ciaosgarage.traveldiary.beans.enums.SqlType;

public class SqlIndex {
    SqlType sqlType;
    Class voInfo;


    public SqlIndex(SqlType sqlType, Class voInfo) {
        this.sqlType = sqlType;
        this.voInfo = voInfo;
    }

    public SqlIndex(Class voInfo, SqlType sqlType) {
        this.sqlType = sqlType;
        this.voInfo = voInfo;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public Class getVoInfo() {
        return voInfo;
    }
}

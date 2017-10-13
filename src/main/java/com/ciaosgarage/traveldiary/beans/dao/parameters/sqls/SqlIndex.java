package com.ciaosgarage.traveldiary.beans.dao.parameters.sqls;


public class SqlIndex {
    private Class voInfo;
    private SqlType type;

    public SqlIndex(Class voInfo, SqlType type) {
        this.voInfo = voInfo;
        this.type = type;
    }

    public Class getVoInfo() {
        return voInfo;
    }

    public SqlType getSqlType() {
        return type;
    }

}

package com.ciaosgarage.traveldiary.beans.dao.sqlStorage;

import com.ciaosgarage.traveldiary.beans.dao.parameters.sqls.SqlIndex;

/**
 * The interface Sql storage.
 * 고정된 SQL을 저장하는 클래스
 */
public interface SqlStorage {
    /**
     * Sql 문을 가져온다
     *
     * @param index 조건 검색
     * @return Sql 문
     */
    String getSql(SqlIndex index);

    /**
     * Sql 문을 저장한다.
     *
     * @param index 조건
     * @param sql   Sql 문
     */
    void setSql(SqlIndex index, String sql);
}

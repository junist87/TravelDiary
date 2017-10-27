package com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement;

import com.ciaosgarage.traveldiary.beans.dao.enums.DatabaseType;
import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;

import java.util.List;

/**
 * Sql 생성에 필요한 클래스
 */
public interface AttachStatement {
    /**
     * 만들어진 Sql 문을 가져온다
     *
     * @return the statement
     */
    String getStatement();

    /**
     * Sql에 입력되는 필드값
     *
     * @return the mapper list
     */
    List<ColumnValue> getMapperList();

    /**
     * Gets database type.
     *
     * @return the database type
     */
    DatabaseType getDatabaseType();
}

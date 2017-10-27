package com.ciaosgarage.traveldiary.beans.dao.resultSetTranslator;

import com.ciaosgarage.traveldiary.beans.dao.enums.DatabaseType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The interface ResultSetTranslator.
 * ResultSet 의 데이터를 지정한 vo 객체로 변환시켜주는 클래스
 */
public interface ResultSetTranslator {
    /**
     * ResultSet 의 데이터를 지정한 vo 객체로 변환시켜주는 메소드
     *
     * @param resultSet 변환할 데이터셋
     * @param voInfo    변환할 객체의 정보를 가지고 있는 class
     * @return 변환된 객체
     * @throws SQLException 변환에 실패시에는 SQLException 이 발생한다.
     */
    Object translate(ResultSet resultSet, Class voInfo) throws SQLException;

    /**
     * 번역하는 데이터베이스의 타입을 리턴하는 메소드
     *
     * @return 데이터베이스 타입
     */
    DatabaseType getDatabaseType();
}
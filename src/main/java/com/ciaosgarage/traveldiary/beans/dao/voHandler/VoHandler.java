package com.ciaosgarage.traveldiary.beans.dao.voHandler;


import com.ciaosgarage.traveldiary.beans.dao.exceptions.InvalidVoObjectException;
import com.ciaosgarage.traveldiary.beans.dao.exceptions.NoExistPrimaryKeyFieldException;

/**
 * The interface VoHandler.
 * VO 객체의 정보나 상태를 검색, 조작하는 클래스
 */
public interface VoHandler {

    /**
     * vo 객체 내부에 primaryKey 가 존재 한다면, 그 값을 추출한다.
     *
     * @param vo 검색할 vo 객체
     * @return primary key 값
     * @throws NoExistPrimaryKeyFieldException primaryKey 값이 없다면 예외를 발생한다.
     */
    Object getPrimaryKey(Object vo) throws NoExistPrimaryKeyFieldException;

    /**
     * vo 객체 내부에 primaryKey 가 존재 한다면, 그 컬럼값을 추출한다.
     *
     * @param vo 검색할 vo 객체
     * @return primary key 값을 가진 컬럼 이름
     * @throws NoExistPrimaryKeyFieldException primaryKey 값이 없다면 예외를 발생한다.
     */
    String getPrimaryKeyColumnName(Object vo) throws NoExistPrimaryKeyFieldException;

    /**
     * class 에 primaryKey 가 존재 한다면, 그 컬럼값을 추출한다.
     *
     * @param voInfo 검색할 vo 객체
     * @return primary key 값을 가진 컬럼 이름
     * @throws NoExistPrimaryKeyFieldException primaryKey 값이 없다면 예외를 발생한다.
     */
    String getPrimaryKeyColumnName(Class voInfo) throws NoExistPrimaryKeyFieldException;

    /**
     * vo 객체 내부에 primaryKey 가 존재 한다면, 그 필드에 값을 입력한다.
     *
     * @param vo          입력할 vo 객체
     * @param insertValue 입력할 값
     * @throws NoExistPrimaryKeyFieldException primaryKey 값이 없다면 예외를 발생한다.
     */
    void setPrimaryKey(Object vo, Object insertValue) throws NoExistPrimaryKeyFieldException;

    /**
     * vo 객체인지 확인하는 메소드
     *
     * @param vo 검사할 객체
     * @throws InvalidVoObjectException vo 객체가 아니라면 예외를 발생한다.
     */
    void isVo(Object vo) throws InvalidVoObjectException;

    /**
     * vo 객체인지 확인하는 메소드
     *
     * @param voInfo 검사할 클래스
     * @throws InvalidVoObjectException vo 객체가 아니라면 예외를 발생한다.
     */
    void isVo(Class voInfo) throws InvalidVoObjectException;

    /**
     * 특정한 필드의 값을 가져온다
     *
     * @param columnName the column name
     * @return the value
     */
    Object getValue(Object vo, String columnName);

    /**
     * 특정한 필드에 값을 입력한다.
     *
     * @param columnName the column name
     * @param value      the value
     * @return 입력된 vo객체
     */
    Object setValue(Object vo, String columnName, Object value);
}
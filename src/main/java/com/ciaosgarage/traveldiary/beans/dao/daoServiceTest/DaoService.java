package com.ciaosgarage.traveldiary.beans.dao.daoServiceTest;

import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

/**
 * The interface DaoService.
 * Dao 를 사용하여 조금더 편하게 데이터베이스를 이용하게 해주는 서비스계층
 */
public interface DaoService {

    /**
     * 데이터를 1개 가져온다.
     *
     * @param voInfo 가저올 도메인 클래스
     * @return 만들어진 도메인 객체
     * @throws EmptyResultDataAccessException 검색하는 조건값이 없다면 발생하는 Exception
     */
    Object pullVo(Class voInfo) throws EmptyResultDataAccessException;

    /**
     * 데이터를 여러개 가져온다.
     *
     * @param voInfo 가저올 도메인 클래스
     * @return 만들어진 도메인 객체
     * @throws EmptyResultDataAccessException 검색하는 조건값이 없다면 발생하는 Exception
     */
    List pullVoList(Class voInfo) throws EmptyResultDataAccessException;

    /**
     * 도메인 객체를 데이터베이스에 등록한다.
     * pk값 조건에 따라 다른 작용을 하게 된다
     * pk값이 존재하면 update 구문으로 데이터를 업데이트 한다.
     * pk값이 존재하지 않으면 SeqTableHandler 를 통하여 primaryKey 값을 부여받은뒤
     * insert 구문으로 데이터베이스에 입력된다.
     *
     * @param vo the vo
     */
    void pushVo(Object vo);


}

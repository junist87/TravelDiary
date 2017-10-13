package com.ciaosgarage.traveldiary.beans.dao.attachStatementHandler;

import com.ciaosgarage.traveldiary.beans.dao.daoService.ColumnCondition;
import com.ciaosgarage.traveldiary.beans.dao.daoService.SortOption;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;

import java.util.List;


/**
 * The interface AttachStatement handler.
 * DaoService 에서 사용되는 클래스
 * AttachStatement 의 관리를 담당한다.
 */
public interface AttachStatementHandler {

    /**
     * 저장된 AttachStatement 를 리턴한다.
     * 리턴 즉시 내부 리스트는 초기화된다.
     *
     * @return
     */
    List<AttachStatement> getAttachStatement();

    /**
     * 정렬에 필요한 쿼리를 담은 AttachStatement 를 추가한다
     *
     * @param sortOption 정렬옵션 (내림차순, 오름차순)
     */
    void addSortOption(SortOption sortOption);

    /**
     * 지정한 인덱스부터 시작하여 지정한 레코드의 갯수만큼 가져오는 AttachStatement 를 추가한다.
     *
     * @param startIndex 시작 인덱스
     * @param unit       가져올 레코드의 단위
     */
    void addUnitExtraction(int startIndex, int unit);

    /**
     * 지정한 레코드 갯수만큼 가져오는 AttachStatement 를 추가한다.
     *
     * @param unit 가져올 레코드의 단위
     */
    void addUnitExtraction(int unit);

    /**
     * 컬럼에 조건을 거는 AttachStatement 를 추가한다.
     *
     * @param conditions "컬럼이름 + 값" 의 필드를 가진 객체
     */
    void addColumnConditions(List<ColumnCondition> conditions);

    /**
     *  컬럼에 조건을 거는 AttachStatement 를 추가한다.
     *
     * @param columnName 컬럼이름
     * @param key        조건
     */
    void addColumnCondition(String columnName, Object key);
}

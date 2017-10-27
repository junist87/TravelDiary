package com.ciaosgarage.traveldiary.beans.dao.attachStatementHandler;

import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.stateLimit.StateLimit;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.stateOrderBy.SOBOperator;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.stateOrderBy.StateOrderBy;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.stateWhere.StateWhere;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class MySqlAttachStatementHandler implements AttachStatementHandler {
    private List<AttachStatement> statementList = new ArrayList<>();

    @Override
    public List<AttachStatement> getAttachStatement() {
        // statementList 초기화 하기 위해 임시 힙변수를 지정한다.
        List returnList = statementList;

        // statementList 초기화
        statementList = new ArrayList<>();

        // 결과값을 리턴한다.
        return returnList;
    }

    @Override
    public void addSortOption(SOBOperator sortOption) {
        switch (sortOption) {
            case ASC:
                statementList.add(new StateOrderBy(SOBOperator.ASC));
                break;
            case DESC:
                statementList.add(new StateOrderBy(SOBOperator.DESC));
                break;
        }
    }

    @Override
    public void addUnitExtraction(int startIndex, int unit) {
        statementList.add(new StateLimit(startIndex, unit));
    }

    @Override
    public void addUnitExtraction(int unit) {
        statementList.add(new StateLimit(unit));
    }

    @Override
    public void addColumnConditions(List<ColumnValue> conditions) {
        statementList.add(new StateWhere(conditions));
    }

    @Override
    public void addColumnCondition(String columnName, Object key) {
        addColumnConditions(Arrays.asList(new ColumnValue(columnName, key)));
    }
}

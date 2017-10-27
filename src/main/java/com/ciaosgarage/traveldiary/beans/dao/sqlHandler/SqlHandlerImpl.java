package com.ciaosgarage.traveldiary.beans.dao.sqlHandler;

import com.ciaosgarage.traveldiary.beans.dao.enums.SqlType;
import com.ciaosgarage.traveldiary.beans.dao.parameters.SqlIndex;
import com.ciaosgarage.traveldiary.beans.dao.parameters.SqlMapper;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;
import com.ciaosgarage.traveldiary.beans.dao.sqlMaker.SqlMaker;
import com.ciaosgarage.traveldiary.beans.dao.sqlMapperMaker.SqlMapperMaker;
import com.ciaosgarage.traveldiary.beans.dao.sqlStorage.SqlStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SqlHandlerImpl implements SqlHandler {

    @Autowired
    private SqlStorage sqlStorage;

    @Autowired
    private SqlMapperMaker sqlMapperMaker;

    @Autowired
    private SqlMaker sqlMaker;

    @Override
    public SqlMapper getSqlMapper(Object vo, SqlType type, List<AttachStatement> statements) {
        Map<String, Object> mapper = sqlMapperMaker.makeMapper(vo, statements);
        String sql = getSql(vo.getClass(), type, statements);
        return new SqlMapper(sql, mapper);
    }

    @Override
    public SqlMapper getSqlMapper(Class voInfo, SqlType type, List<AttachStatement> statements) {
        Map<String, Object> mapper = sqlMapperMaker.makeMapper(voInfo, statements);
        String sql = getSql(voInfo, type, statements);
        return new SqlMapper(sql, mapper);
    }

    @Override
    public SqlMapper getSqlMapper(Class voInfo, SqlType type) {
        String sql = getSql(voInfo, type);
        return new SqlMapper(sql, new HashMap<>());
    }

    @Override
    public SqlMapper getSqlMapper(Object vo, SqlType type) {
        String sql = getSql(vo.getClass(), type);
        Map<String, Object> mapper = sqlMapperMaker.makeMapper(vo);
        return new SqlMapper(sql, mapper);
    }

    private String getSql(Class voInfo, SqlType type, List<AttachStatement> statements) {
        // 기본형 sql 을 불러온다.
        String sql = getSql(voInfo, type);

        // attachStatement 이 있다면 붙혀준다.
        if (statements != null) sql = this.appendStatementSql(sql, statements);
        return sql;
    }

    private String getSql(Class voInfo, SqlType type) {
        SqlIndex index = new SqlIndex(voInfo, type);
        String sql = sqlStorage.getSql(index);

        // 저장된 sql 이 없다면 새로 생성하여 저장한다.
        if (sql == null) {
            sql = getNewSql(voInfo, type);
            sqlStorage.setSql(index, sql);
        }

        return sql;
    }

    private String getNewSql(Class voInfo, SqlType type) {
        switch (type) {
            case SELECT:
                return sqlMaker.select(voInfo);
            case INSERT:
                return sqlMaker.insert(voInfo);
            case DELETE:
                return sqlMaker.delete(voInfo);
            case UPDATE:
                return sqlMaker.update(voInfo);
            case DELETEALL:
                return sqlMaker.deleteAll(voInfo);
            case COUNT:
                return sqlMaker.count(voInfo);
            default:
                throw new RuntimeException("!- Can't Make SQL -!");
        }
    }

    private String appendStatementSql(String sql, List<AttachStatement> statements) {
        StringBuffer sqlBuffer = new StringBuffer(sql);
        for (AttachStatement statement : statements) {
            sqlBuffer.append(" " + statement.getStatement());
        }
        return sqlBuffer.toString();
    }
}

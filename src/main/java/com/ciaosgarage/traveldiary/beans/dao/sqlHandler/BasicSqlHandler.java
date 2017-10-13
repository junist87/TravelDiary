package com.ciaosgarage.traveldiary.beans.dao.sqlHandler;

import com.ciaosgarage.traveldiary.beans.dao.parameters.sqls.SqlIndex;
import com.ciaosgarage.traveldiary.beans.dao.parameters.sqls.SqlProduct;
import com.ciaosgarage.traveldiary.beans.dao.parameters.sqls.SqlType;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;
import com.ciaosgarage.traveldiary.beans.dao.sqlMaker.SqlMaker;
import com.ciaosgarage.traveldiary.beans.dao.sqlMapperMaker.SqlMapperMaker;
import com.ciaosgarage.traveldiary.beans.dao.sqlStorage.SqlStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BasicSqlHandler implements SqlHandler {

    @Autowired
    private SqlStorage sqlStorage;

    @Autowired
    private SqlMapperMaker sqlMapperMaker;

    @Autowired
    private SqlMaker sqlMaker;

    @Override
    public SqlProduct getSqlProduct(Object vo, SqlType type, List<AttachStatement> statements) {
        Map<String, Object> mapper = sqlMapperMaker.makeMapper(vo, statements);
        String sql = getSql(vo.getClass(), type, statements);
        return new SqlProduct(sql, mapper);
    }

    @Override
    public SqlProduct getSqlProduct(Class voInfo, SqlType type, List<AttachStatement> statements) {
        Map<String, Object> mapper = sqlMapperMaker.makeMapper(statements);
        String sql = getSql(voInfo, type, statements);
        return new SqlProduct(sql, mapper);
    }

    @Override
    public SqlProduct getSqlProduct(Class voInfo, SqlType type) {
        String sql = getSql(voInfo, type, null);
        return new SqlProduct(sql, new HashMap<>());
    }

    @Override
    public SqlProduct getSqlProduct(Object vo, SqlType type) {
        String sql = getSql(vo.getClass(), type, null);
        Map<String, Object> mapper = sqlMapperMaker.makeMapper(vo);
        return new SqlProduct(sql, mapper);
    }

    private String getSql(Class voInfo, SqlType type, List<AttachStatement> statements) {
        SqlIndex index = new SqlIndex(voInfo, type);
        String sql = sqlStorage.getSql(index);

        // 저장된 sql 이 없다면 새로 생성하여 저장한다.
        if (sql == null) {
            sql = getNewSql(voInfo, type);
            sqlStorage.setSql(index, sql);
        }
        // attachStatement 이 있다면 붙혀준다.
        if (statements != null) sql = this.appendStatementSql(sql, statements);
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

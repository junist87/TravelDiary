package com.ciaosgarage.traveldiary.beans.dao.dao;

import com.ciaosgarage.traveldiary.beans.dao.enums.SqlType;
import com.ciaosgarage.traveldiary.beans.dao.exceptions.NoExistPrimaryKeyFieldException;
import com.ciaosgarage.traveldiary.beans.dao.parameters.SqlMapper;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;
import com.ciaosgarage.traveldiary.beans.dao.sqlExecutor.SqlExecutor;
import com.ciaosgarage.traveldiary.beans.dao.sqlHandler.SqlHandler;
import com.ciaosgarage.traveldiary.beans.dao.voHandler.VoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class DaoImpl implements Dao {
    @Autowired
    private SqlHandler sqlHandler;

    @Autowired
    private SqlExecutor sqlExecutor;

    @Autowired
    private VoHandler voHandler;

    @Override
    public int add(Object vo) {
        SqlMapper sqlProduct = sqlHandler.getSqlMapper(vo, SqlType.INSERT);
        return sqlExecutor.update(sqlProduct.getSql(), sqlProduct.getMapper());
    }

    @Override
    public Object get(Class voInfo, List<AttachStatement> statements) throws EmptyResultDataAccessException {
        SqlMapper sqlProduct = sqlHandler.getSqlMapper(voInfo, SqlType.SELECT, statements);
        return sqlExecutor.queryForObject(voInfo, sqlProduct.getSql(), sqlProduct.getMapper());
    }

    @Override
    public List getList(Class voInfo, List<AttachStatement> statements) throws EmptyResultDataAccessException {
        SqlMapper sqlProduct = sqlHandler.getSqlMapper(voInfo, SqlType.SELECT, statements);
        return sqlExecutor.query(voInfo, sqlProduct.getSql(), sqlProduct.getMapper());
    }

    @Override
    public int update(Object vo) throws NoExistPrimaryKeyFieldException {
        // primary key 값이 기준이므로 체크한다.
        if (voHandler.getPrimaryKey(vo) == null) throw new NoExistPrimaryKeyFieldException();
        SqlMapper sqlProduct = sqlHandler.getSqlMapper(vo, SqlType.UPDATE);
        return sqlExecutor.update(sqlProduct.getSql(), sqlProduct.getMapper());
    }

    @Override
    public int delete(Object vo) {
        SqlMapper sqlProduct = sqlHandler.getSqlMapper(vo, SqlType.DELETE);
        return sqlExecutor.update(sqlProduct.getSql(), sqlProduct.getMapper());
    }

    @Override

    public int count(Class voInfo) {
        SqlMapper sqlProduct = sqlHandler.getSqlMapper(voInfo, SqlType.COUNT);
        return sqlExecutor.count(sqlProduct.getSql());
    }

    @Override
    public int deleteAll(Class voInfo) {
        SqlMapper sqlProduct = sqlHandler.getSqlMapper(voInfo, SqlType.DELETEALL);
        return sqlExecutor.update(sqlProduct.getSql(), sqlProduct.getMapper());
    }
}

package com.ciaosgarage.traveldiary.beans.dao.dao;

import com.ciaosgarage.traveldiary.beans.dao.exceptions.NoExistPrimaryKeyField;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;
import com.ciaosgarage.traveldiary.beans.dao.parameters.sqls.SqlProduct;
import com.ciaosgarage.traveldiary.beans.dao.parameters.sqls.SqlType;
import com.ciaosgarage.traveldiary.beans.dao.sqlExecutor.SqlExecutor;
import com.ciaosgarage.traveldiary.beans.dao.sqlHandler.SqlHandler;
import com.ciaosgarage.traveldiary.beans.dao.voHandler.VoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class BasicDao implements Dao {
    @Autowired
    private SqlHandler sqlHandler;

    @Autowired
    private SqlExecutor sqlExecutor;

    @Autowired
    private VoHandler voHandler;

    @Override
    public int add(Object vo) {
        SqlProduct sqlProduct = sqlHandler.getSqlProduct(vo, SqlType.INSERT);
        return sqlExecutor.update(sqlProduct.getSql(), sqlProduct.getMapper());
    }

    @Override
    public Object get(Class voInfo, List<AttachStatement> statements) {
        SqlProduct sqlProduct = sqlHandler.getSqlProduct(voInfo, SqlType.SELECT, statements);
        return sqlExecutor.queryForObject(voInfo, sqlProduct.getSql(), sqlProduct.getMapper());
    }

    @Override
    public List getList(Class voInfo, List<AttachStatement> statements) {
        SqlProduct sqlProduct = sqlHandler.getSqlProduct(voInfo, SqlType.SELECT, statements);
        return sqlExecutor.query(voInfo, sqlProduct.getSql(), sqlProduct.getMapper());
    }

    @Override
    public int update(Object vo) throws NoExistPrimaryKeyField{
        // primary key 값이 기준이므로 체크한다.
        if (voHandler.getPrimaryKey(vo) == null) throw new NoExistPrimaryKeyField();
        SqlProduct sqlProduct = sqlHandler.getSqlProduct(vo, SqlType.UPDATE);
        return sqlExecutor.update(sqlProduct.getSql(), sqlProduct.getMapper());
    }

    @Override
    public int delete(Object vo) {
        SqlProduct sqlProduct = sqlHandler.getSqlProduct(vo, SqlType.DELETE);
        return sqlExecutor.update(sqlProduct.getSql(), sqlProduct.getMapper());
    }

    @Override

    public int count(Class voInfo) {
        SqlProduct sqlProduct = sqlHandler.getSqlProduct(voInfo, SqlType.COUNT);
        return sqlExecutor.count(sqlProduct.getSql());
    }

    @Override
    public int deleteAll(Class voInfo) {
        SqlProduct sqlProduct = sqlHandler.getSqlProduct(voInfo, SqlType.DELETEALL);
        return sqlExecutor.update(sqlProduct.getSql(), sqlProduct.getMapper());
    }
}

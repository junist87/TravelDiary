package com.ciaosgarage.traveldiary.beans.dao.sqlExecutor;

import com.ciaosgarage.traveldiary.beans.dao.resultSetTranslator.ResultSetTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class NamedParameterJdbcTemplateSqlExecutor implements SqlExecutor {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ResultSetTranslator resultSetTranslator;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public int update(String sql, Map<String, Object> parameters) {
        return jdbcTemplate.update(sql, parameters);
    }

    @Override
    public Object queryForObject(Class voInfo, String sql, Map<String, Object> parameters) throws EmptyResultDataAccessException {
        Object getObject = jdbcTemplate.queryForObject(sql, parameters, (resultSet, i) -> resultSetTranslator.translate(resultSet, voInfo));
        return getObject;
    }

    @Override
    public List query(Class voInfo, String sql, Map<String, Object> parameters) throws EmptyResultDataAccessException {
        List getList = jdbcTemplate.query(sql, parameters, (resultSet, i) -> resultSetTranslator.translate(resultSet, voInfo));
        if (getList.size() == 0) throw new EmptyResultDataAccessException(0);
        return getList;
    }

    @Override
    public int count(String sql) {
        return jdbcTemplate.queryForObject(sql, new HashMap<>(), (resultSet, i) -> resultSet.getInt(1));
    }
}

package com.ciaosgarage.traveldiary.beans.dao.sqlExecutor;

import com.ciaosgarage.traveldiary.beans.dao.resultSetTranslator.ResultSetTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class BasicSqlExecutor implements SqlExecutor {

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
    public Object queryForObject(Class voInfo, String sql, Map<String, Object> parameters) {
        return jdbcTemplate.queryForObject(sql, parameters, (resultSet, i) -> resultSetTranslator.translate(resultSet, voInfo));
    }

    @Override
    public List<Object> query(Class voInfo, String sql, Map<String, Object> parameters) {
        return jdbcTemplate.query(sql, parameters, (resultSet, i) -> resultSetTranslator.translate(resultSet, voInfo));
    }

    @Override
    public int count(String sql) {
        return jdbcTemplate.queryForObject(sql, new HashMap<>(), (resultSet, i) -> resultSet.getInt(1));
    }
}

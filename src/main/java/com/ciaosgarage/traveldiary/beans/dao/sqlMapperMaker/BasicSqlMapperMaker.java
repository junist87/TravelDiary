package com.ciaosgarage.traveldiary.beans.dao.sqlMapperMaker;

import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BasicSqlMapperMaker implements SqlMapperMaker {

    @Override
    public Map<String, Object> makeMapper(Object vo, List<AttachStatement> statements) {
        Map<String, Object> mapper = new HashMap<>();
        mapper.putAll(getMapperToVo(vo));
        mapper.putAll(getMapperToAttachStatements(statements));
        return mapper;
    }

    @Override
    public Map<String, Object> makeMapper(List<AttachStatement> statements) {
        Map<String, Object> mapper = new HashMap<>();
        mapper.putAll(getMapperToAttachStatements(statements));
        return mapper;
    }

    @Override
    public Map<String, Object> makeMapper(Object vo) {
        return getMapperToVo(vo);
    }

    private Map<String, Object> getMapperToAttachStatements(List<AttachStatement> statements) {
        Map<String, Object> mapper = new HashMap<>();
        for (AttachStatement statement : statements) {
            mapper.putAll(statement.getMapper());
        }
        return mapper;
    }

    private Map<String, Object> getMapperToVo(Object vo) {
        Map<String, Object> mapper = new HashMap<>();
        return getMapperToVoFromSuperClass(vo, vo.getClass(), mapper);
    }

    // 상속받은 모든 클래스의 필드까지 검색하는 메소드
    private Map<String, Object> getMapperToVoFromSuperClass(Object vo, Class targetClass, Map<String, Object> mapper) {
        for (Field field : targetClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                mapper.put(field.getName(), field.get(vo));
            } catch (IllegalAccessException e) {
            }
        }
        if (targetClass.getSuperclass() != null)
            return getMapperToVoFromSuperClass(vo, targetClass.getSuperclass(), mapper);
        else return mapper;
    }

}

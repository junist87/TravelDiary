package com.ciaosgarage.traveldiary.beans.sqlMapperMaker;

import com.ciaosgarage.traveldiary.beans.cryptor.Cryptor;
import com.ciaosgarage.traveldiary.beans.exception.CannotAccessFieldValue;
import com.ciaosgarage.traveldiary.beans.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.beans.parameters.attachStatement.AttachStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.*;

@Repository
public class SqlMapperMakerImpl implements SqlMapperMaker {

    @Autowired
    Cryptor cryptor;

    @Override
    public Map<String, Object> makeMapper(Object vo, List<AttachStatement> statements) {
        Map<String, Object> mapper = new HashMap<>();
        mapper.putAll(makeMapper(vo));
        mapper.putAll(makeMapper(vo.getClass(), statements));
        return mapper;
    }

    @Override
    public Map<String, Object> makeMapper(Object vo) {
        List<ColumnValue> list = this.getFieldValues(vo, vo.getClass(), new ArrayList<>());
        return swapMapper(vo.getClass(), list);
    }

    @Override
    public Map<String, Object> makeMapper(Class voInfo, List<AttachStatement> statements) {
        List<ColumnValue> list = this.swapAttachStatementMapper(statements);
        return swapMapper(voInfo, list);
    }

    private List<ColumnValue> swapAttachStatementMapper(List<AttachStatement> statements) {
        List<ColumnValue> list = new ArrayList<>();
        for (AttachStatement statement : statements) {
            list.addAll(statement.getMapperList());
        }
        return list;
    }

    private Map<String, Object> swapMapper(Class voInfo, List<ColumnValue> list) {
        Map<String, Object> mapper = new HashMap<>();
        for (ColumnValue value : list) {
            mapper.put(value.getColumnName(), cryptor.encryption(voInfo, value));
        }
        return mapper;
    }


    // recursive 를 이용하여 객체를 구성하는 모든 클래스의 필드를 변환한다
    private List<ColumnValue> getFieldValues(Object vo, Class targetClass, List<ColumnValue> list) {
        for (Field field : targetClass.getDeclaredFields()) {
            list.add(new ColumnValue(field.getName(), this.getVoValue(vo, field)));
        }

        // 슈퍼 클래스까지 검색하여 리스트를 만든다
        if (targetClass.getSuperclass() != null) {
            return this.getFieldValues(vo, targetClass.getSuperclass(), list);
        } else {
            return list;
        }
    }

    private Object getVoValue(Object vo, Field targetField) {
        try {
            targetField.setAccessible(true);
            return targetField.get(vo);
        } catch (IllegalAccessException e) {
            throw new CannotAccessFieldValue();
        }
    }
}

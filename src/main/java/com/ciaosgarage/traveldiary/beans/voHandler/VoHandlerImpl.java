package com.ciaosgarage.traveldiary.beans.voHandler;

import com.ciaosgarage.traveldiary.beans.exception.CannotAccessFieldValue;
import com.ciaosgarage.traveldiary.beans.exception.InvalidVoObject;
import com.ciaosgarage.traveldiary.beans.exception.NoExistPrimaryKeyField;
import com.ciaosgarage.traveldiary.beans.vo.ColumnConfig;
import com.ciaosgarage.traveldiary.beans.vo.ColumnType;
import com.ciaosgarage.traveldiary.beans.vo.DbTable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;

@Repository
public class VoHandlerImpl implements VoHandler {

    @Override
    public Object getPrimaryKey(Object vo) throws NoExistPrimaryKeyField {
        try {
            String fieldName = getPrimaryKeyFieldName(vo.getClass());
            Field primaryKeyField = vo.getClass().getDeclaredField(fieldName);
            primaryKeyField.setAccessible(true);
            return primaryKeyField.get(vo);
        } catch (Exception e) {
            throw new NoExistPrimaryKeyField("!- Can't access primary key field");
        }
    }

    @Override
    public String getPrimaryKeyColumnName(Object vo) throws NoExistPrimaryKeyField {
        return getPrimaryKeyFieldName(vo.getClass());
    }

    @Override
    public String getPrimaryKeyColumnName(Class voInfo) throws NoExistPrimaryKeyField {
        return getPrimaryKeyFieldName(voInfo);
    }

    @Override
    public void setPrimaryKey(Object vo, Object insertValue) throws NoExistPrimaryKeyField {
        try {
            String fieldName = getPrimaryKeyFieldName(vo.getClass());
            Field primaryKeyField = vo.getClass().getDeclaredField(fieldName);
            primaryKeyField.setAccessible(true);
            primaryKeyField.set(vo, insertValue);
        } catch (Exception e) {
            throw new NoExistPrimaryKeyField("!- Can't access primary key field");
        }
    }

    @Override
    public void isVo(Object vo) throws InvalidVoObject {
        isVo(vo.getClass());
    }

    // 클래스를 구성하는 모든 클래스를 검색하여 DbTable 어노테이션을 찾는다
    @Override
    public void isVo(Class voInfo) throws InvalidVoObject {
        if (!voInfo.isAnnotationPresent(DbTable.class)) {
            if (voInfo.getSuperclass() != null) isVo(voInfo.getSuperclass());
            else throw new InvalidVoObject();
        }
    }

    @Override
    public Object getValue(Object vo, String columnName) {
        try {
            Field targetField = vo.getClass().getDeclaredField(columnName);
            targetField.setAccessible(true);
            return targetField.get(vo);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new CannotAccessFieldValue("!- ColumnName : " + columnName + ", vo : " + vo.getClass().getSimpleName());
        }
    }

    @Override
    public Object setValue(Object vo, String columnName, Object value) {
        try {
            Field targetField = vo.getClass().getDeclaredField(columnName);
            targetField.setAccessible(true);
            targetField.set(vo, value);
            return vo;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new CannotAccessFieldValue("!- ColumnName : " + columnName + ", vo : " + vo.getClass().getSimpleName());
        }
    }

    // 객체를 구성하는 모든 클래스에서 PrimaryKey 어노테이션을 가진 필드값을 찾는다.
    // Recursive Pattern 을 이용하여 모든 super class 검색
    private String getPrimaryKeyFieldName(Class targetClass) {
        for (Field field : targetClass.getDeclaredFields()) {
            if (isPrimaryKey(field)) return field.getName();
        }
        if (targetClass.getSuperclass() != null) {
            return getPrimaryKeyFieldName(targetClass.getSuperclass());
        } else throw new NoExistPrimaryKeyField();
    }

    private boolean isPrimaryKey(Field field) {
        field.setAccessible(true);
        // 컬럼셋팅 어노테이션이 있다면 키 값을 검색
        if (field.isAnnotationPresent(ColumnConfig.class)) {
            ColumnConfig settings = field.getAnnotation(ColumnConfig.class);
            if (settings.columnType().equals(ColumnType.PRIMARYKEY)) return true;
        }
        return false;
    }

}
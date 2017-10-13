package com.ciaosgarage.traveldiary.beans.dao.voHandler;

import com.ciaosgarage.traveldiary.beans.dao.valueObject.DbTable;
import com.ciaosgarage.traveldiary.beans.dao.valueObject.PrimaryKey;
import com.ciaosgarage.traveldiary.beans.dao.exceptions.InvalidVoObject;
import com.ciaosgarage.traveldiary.beans.dao.exceptions.NoExistPrimaryKeyField;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;

@Repository
public class BasicVoHandler implements VoHandler {

    @Override
    public Object getPrimaryKey(Object vo) throws NoExistPrimaryKeyField {
        try {
            String fieldName = getPrimaryKeyFieldName(vo.getClass());
            Field  primaryKeyField = vo.getClass().getDeclaredField(fieldName);
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
    public void setPrimaryKey(Object vo, Object insertValue) throws NoExistPrimaryKeyField {
        try {
            String fieldName = getPrimaryKeyFieldName(vo.getClass());
            Field  primaryKeyField = vo.getClass().getDeclaredField(fieldName);
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

    // 객체를 구성하는 모든 클래스에서 PrimaryKey 어노테이션을 가진 필드값을 찾는다.
    // Recursive Pattern 을 이용하여 모든 super class 검색
    private String getPrimaryKeyFieldName(Class targetClass) {
        for (Field field: targetClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(PrimaryKey.class)) return field.getName();
        }
        if (targetClass.getSuperclass() != null) {
            return getPrimaryKeyFieldName(targetClass.getSuperclass());
        }
        else throw new NoExistPrimaryKeyField();
    }

}

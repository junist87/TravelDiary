package com.ciaosgarage.traveldiary.beans.dao.sqlMaker;

import com.ciaosgarage.traveldiary.beans.dao.valueObject.ColumnConfig;
import com.ciaosgarage.traveldiary.beans.dao.valueObject.PrimaryKey;
import com.ciaosgarage.traveldiary.beans.dao.valueObject.RwType;
import com.ciaosgarage.traveldiary.beans.dao.daoSettings.DaoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;


@Repository
public class BasicSqlMaker implements SqlMaker {

    @Autowired
    DaoSettings daoSettings;

    @Override
    public String update(Class voInfo) {
        StringBuffer sql = new StringBuffer("UPDATE " + daoSettings.tablePrefix + voInfo.getSimpleName() + " SET ");
        sql.append(getUpdateValues(voInfo));
        sql.append(" " + getWherePrimaryKey(voInfo));
        return sql.toString();
    }

    @Override
    public String insert(Class voInfo) {
        StringBuffer sql = new StringBuffer("INSERT INTO " + daoSettings.tablePrefix + voInfo.getSimpleName());
        sql.append(" " + getInsertValue(voInfo));
        return sql.toString();
    }

    @Override
    public String delete(Class voInfo) {
        StringBuffer sql = new StringBuffer("DELETE FROM " + daoSettings.tablePrefix + voInfo.getSimpleName());
        sql.append(" " + getWherePrimaryKey(voInfo));
        return sql.toString();
    }

    @Override
    public String deleteAll(Class voInfo) {
        StringBuffer sql = new StringBuffer("DELETE FROM " + daoSettings.tablePrefix + voInfo.getSimpleName());
        return sql.toString();
    }

    @Override
    public String select(Class voInfo) {
        StringBuffer sql = new StringBuffer("SELECT * FROM " + daoSettings.tablePrefix + voInfo.getSimpleName());
        return sql.toString();
    }

    @Override
    public String count(Class voInfo) {
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + daoSettings.tablePrefix + voInfo.getSimpleName());
        return sql.toString();
    }

    private String getUpdateValues(Class voInfo) {
        StringBuffer sql = new StringBuffer();
        for (Field field : voInfo.getDeclaredFields()) {
            field.setAccessible(true);
            if (getRwType(field).equals(RwType.EDITABLE)) {
                sql.append(field.getName() + " = :" + field.getName() + ", ");
            }
        }
        return sql.substring(0, sql.length() - 2);
    }

    private String getWherePrimaryKey(Class voInfo) {
        for (Field field : voInfo.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                return "WHERE " + field.getName() + " = :" + field.getName();
            }
        }
        return null;
    }

    private String getInsertValue(Class voInfo) {
        StringBuffer columns = new StringBuffer();
        StringBuffer values = new StringBuffer();

        for (Field field : voInfo.getDeclaredFields()) {
            field.setAccessible(true);
            if (!getRwType(field).equals(RwType.READONLY)) {
                columns.append(field.getName() + ", ");
                values.append(":" + field.getName() + ", ");
            }
        }
        String sql = "(" + columns.substring(0, columns.length() - 2)
                + ") VALUES (" + values.substring(0, values.length() - 2) + ")";
        return sql;
    }

    private RwType getRwType(Field field) {
        RwType rwType = RwType.EDITABLE;
        if (field.isAnnotationPresent(PrimaryKey.class)) {
            PrimaryKey columnConfig = field.getAnnotation(PrimaryKey.class);
            rwType = columnConfig.rwType();
        }

        if (field.isAnnotationPresent(ColumnConfig.class)) {
            ColumnConfig columnConfig = field.getAnnotation(ColumnConfig.class);
            rwType = columnConfig.rwType();
        }

        return rwType;
    }
}

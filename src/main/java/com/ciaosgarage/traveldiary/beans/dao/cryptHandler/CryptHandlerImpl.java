package com.ciaosgarage.traveldiary.beans.dao.cryptHandler;

import com.ciaosgarage.traveldiary.beans.cryptor.Cryptor;
import com.ciaosgarage.traveldiary.beans.dao.enums.DataType;
import com.ciaosgarage.traveldiary.beans.dao.exceptions.CannotAccessFieldValueException;
import com.ciaosgarage.traveldiary.beans.dao.exceptions.CannotDefineDataTypeException;
import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.beans.dao.vo.ColumnConfig;
import com.ciaosgarage.traveldiary.beans.dao.vo.CryptOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.Date;

@Repository
public class CryptHandlerImpl implements CryptHandler {

    @Autowired
    Cryptor daoCrpytor;


    @Override
    public Object encryption(Class voInfo, ColumnValue value) {
        if (this.isCryption(voInfo, value.getColumnName())) {
            return daoCrpytor.encryption(String.valueOf(value.getValue()));
        }
        return value.getValue();
    }

    @Override
    public Object decryption(Class voInfo, ColumnValue value) {
        if (this.isCryption(voInfo, value.getColumnName())) {
            String decryptionValue = daoCrpytor.decryption((String) value.getValue());
            return this.valueOf(decryptionValue, this.getDataType(voInfo, value));
        }
        return value.getValue();
    }

    private Object valueOf(String value, DataType dataType) {
        switch (dataType) {
            case DATE:
                return Date.valueOf(value);
            case LONG:
                return Long.valueOf(value);
            case DOUBLE:
                return Double.valueOf(value);
            case STRING:
                return value;
            case INTEGER:
                return Integer.valueOf(value);
            default:
                throw new CannotDefineDataTypeException("!- Not inserted DataType : " + dataType);
        }
    }


    private DataType getDataType(Class voInfo, ColumnValue value) {
        try {
            Field targetField = voInfo.getDeclaredField(value.getColumnName());
            switch (targetField.getType().getSimpleName()) {
                case "Integer":
                    return DataType.INTEGER;
                case "String":
                    return DataType.STRING;
                case "Double":
                    return DataType.DOUBLE;
                case "Date":
                    return DataType.DATE;
                case "Long":
                    return DataType.LONG;
                default:
                    throw new CannotDefineDataTypeException();
            }
        } catch (NoSuchFieldException e) {
            throw new CannotAccessFieldValueException();
        }
    }


    private boolean isCryption(Class voInfo, String columnname) {
        try {
            // 해당 필드에 접근한다
            Field targetField = voInfo.getDeclaredField(columnname);
            targetField.setAccessible(true);

            // 필드에 어노테이션 정보를 추출한다.
            ColumnConfig settings = targetField.getAnnotation(ColumnConfig.class);
            if ((settings != null) &&
                    (settings.cryptOption().equals(CryptOption.ON))) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }


}

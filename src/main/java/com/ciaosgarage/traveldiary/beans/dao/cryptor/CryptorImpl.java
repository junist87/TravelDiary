package com.ciaosgarage.traveldiary.beans.dao.cryptor;

import com.ciaosgarage.traveldiary.beans.dao.daoSettings.DaoSettings;
import com.ciaosgarage.traveldiary.beans.dao.enums.DataType;
import com.ciaosgarage.traveldiary.beans.dao.exception.CannotAccessFieldValue;
import com.ciaosgarage.traveldiary.beans.dao.exception.CannotDefineDataType;
import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.beans.dao.vo.ColumnConfig;
import com.ciaosgarage.traveldiary.beans.dao.vo.CryptOption;
import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.Key;
import java.sql.Date;

@Repository
public class CryptorImpl implements Cryptor {

    @Autowired
    DaoSettings daoSettings;

    // 암호화에 필요한 필드값
    String iv;
    Key keySpec;


    // 모든 bean이 초기화 된 후(DaoSetting 이 생성된 후)에, 키값을 생성한다.
    @PostConstruct
    private void setup() {
        makeKey(daoSettings.cryptionKey);
    }

    @Override
    public Object encryption(Class voInfo, ColumnValue value) {
        if (this.isCryption(voInfo, value.getColumnName())) {
            return encrypt(String.valueOf(value.getValue()));
        }
        return value.getValue();
    }

    @Override
    public Object decryption(Class voInfo, ColumnValue value) {
        if (this.isCryption(voInfo, value.getColumnName())) {
            String decryptionValue = decrypt((String) value.getValue());
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
                throw new CannotDefineDataType("!- Not inserted DataType : " + dataType);
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
                    throw new CannotDefineDataType();
            }
        } catch (NoSuchFieldException e) {
            throw new CannotAccessFieldValue();
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

    private void makeKey(String key) {
        if (key.length() < 16) throw new RuntimeException("!- Can't make Key AES-256 -!");
        try {
            this.iv = key.substring(0, 16);
            byte[] keyBytes = new byte[16];
            byte[] b = key.getBytes("UTF-8");
            int len = b.length;
            if (len > keyBytes.length) {
                len = keyBytes.length;
            }
            System.arraycopy(b, 0, keyBytes, 0, len);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            this.keySpec = keySpec;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private String encrypt(String str) {
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
            byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
            String enStr = new String(Base64.encodeBase64(encrypted));
            return enStr;
        } catch (Exception e) {
            return null;
        }
    }

    private String decrypt(String str) {
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
            byte[] byteStr = Base64.decodeBase64(str.getBytes());
            return new String(c.doFinal(byteStr), "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }
}

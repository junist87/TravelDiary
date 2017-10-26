package com.ciaosgarage.traveldiary.beans.cryptor;

import com.ciaosgarage.traveldiary.beans.daoSettings.DaoSettings;
import com.ciaosgarage.traveldiary.beans.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.beans.vo.ColumnConfig;
import com.ciaosgarage.traveldiary.beans.vo.CryptOption;
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


@Repository
public class CryptorImpl implements Cryptor {

    @Autowired
    DaoSettings daoSettings;

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
            return decrypt(String.valueOf(value.getValue()));
        }
        return value.getValue();
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
        } catch (NoSuchFieldException e) {
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

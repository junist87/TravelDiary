package com.ciaosgarage.traveldiary.playground;


import com.ciaosgarage.traveldiary.beans.dao.cryptHandler.CryptHandler;
import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.context.BeansContext;
import com.ciaosgarage.traveldiary.domain.account.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeansContext.class)
public class CryptHandlerTest {

    @Autowired
    CryptHandler cryptHandler;


    @Test
    public void encryptValue() {
        String getEncryptValue01 = (String) cryptHandler.encryption(Account.class, new ColumnValue("email", "DAEFD#FDABDAFD"));
        String getEncryptValue02 = (String) cryptHandler.encryption(Account.class, new ColumnValue("email", "DAEFD#FDABDAFD"));
        String getEncryptValue03 = (String) cryptHandler.encryption(Account.class, new ColumnValue("email", "DAEFD#FDABDAFD"));

        System.out.println(getEncryptValue01);
        System.out.println(getEncryptValue02);
        System.out.println(getEncryptValue03);

        /*
        * 암호화된 키값은 매번 같은 값을 가지게 된다.
        *
         */
    }
}

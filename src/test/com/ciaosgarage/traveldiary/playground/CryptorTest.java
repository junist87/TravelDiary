package com.ciaosgarage.traveldiary.playground;


import com.ciaosgarage.traveldiary.beans.dao.cryptor.Cryptor;
import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.context.DaoContext;
import com.ciaosgarage.traveldiary.domain.account.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoContext.class)
public class CryptorTest {

    @Autowired
    Cryptor cryptor;


    @Test
    public void encryptValue() {
        String getEncryptValue01 = (String) cryptor.encryption(Account.class, new ColumnValue("email", "DAEFD#FDABDAFD"));
        String getEncryptValue02 = (String) cryptor.encryption(Account.class, new ColumnValue("email", "DAEFD#FDABDAFD"));
        String getEncryptValue03 = (String) cryptor.encryption(Account.class, new ColumnValue("email", "DAEFD#FDABDAFD"));

        System.out.println(getEncryptValue01);
        System.out.println(getEncryptValue02);
        System.out.println(getEncryptValue03);

        /*
        * 암호화된 키값은 매번 같은 값을 가지게 된다.
        *
         */
    }
}

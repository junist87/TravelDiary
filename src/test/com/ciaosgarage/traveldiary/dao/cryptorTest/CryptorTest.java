package com.ciaosgarage.traveldiary.dao.cryptorTest;


import com.ciaosgarage.traveldiary.beans.cryptor.Cryptor;
import com.ciaosgarage.traveldiary.beans.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.context.DaoContext;
import com.ciaosgarage.traveldiary.domain.account.Account;
import com.ciaosgarage.traveldiary.sampler.AccountSampler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoContext.class)
public class CryptorTest {
    @Autowired
    Cryptor cryptor;

    AccountSampler accountSampler;

    @Before
    public void setup() {
        accountSampler = new AccountSampler();
        assertNotNull(cryptor);
    }

    @Test
    public void cryptest() {
        List<TestValues> list = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Account account = accountSampler.getRandomAccount();
            TestValues values = new TestValues();
            values.value = account.getEmail();
            values.encryptValue = cryptor.encryption(Account.class, new ColumnValue("email", account.getEmail()));
            list.add(values);
        }

        for (TestValues values : list) {
            assertThat(values.value, is(cryptor.decryption(Account.class, new ColumnValue("email", values.encryptValue))));
        }
    }

    class TestValues {
        public String value;
        public Object encryptValue;
    }

}

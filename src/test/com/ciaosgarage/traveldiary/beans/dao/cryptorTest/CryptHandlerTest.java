package com.ciaosgarage.traveldiary.beans.dao.cryptorTest;


import com.ciaosgarage.traveldiary.beans.dao.cryptHandler.CryptHandler;
import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.context.BeansContext;
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
@ContextConfiguration(classes = BeansContext.class)
public class CryptHandlerTest {
    @Autowired
    CryptHandler cryptHandler;

    AccountSampler accountSampler;

    @Before
    public void setup() {
        accountSampler = new AccountSampler();
        assertNotNull(cryptHandler);
    }

    @Test
    public void cryptest() {
        List<TestValues> list = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Account account = accountSampler.getRandomAccount();
            TestValues values = new TestValues();
            values.value = account.getEmail();
            values.encryptValue = cryptHandler.encryption(Account.class, new ColumnValue("email", account.getEmail()));
            list.add(values);
        }

        for (TestValues values : list) {
            assertThat(values.value, is(cryptHandler.decryption(Account.class, new ColumnValue("email", values.encryptValue))));
        }
    }

    class TestValues {
        public String value;
        public Object encryptValue;
    }

}

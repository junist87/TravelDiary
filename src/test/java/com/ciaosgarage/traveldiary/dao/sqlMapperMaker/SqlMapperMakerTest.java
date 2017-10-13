package com.ciaosgarage.traveldiary.dao.sqlMapperMaker;

import com.ciaosgarage.traveldiary.sampler.AccountSampler;
import com.ciaosgarage.traveldiary.beans.dao.sqlMapperMaker.SqlMapperMaker;
import com.ciaosgarage.traveldiary.context.TravelDiaryContext;
import com.ciaosgarage.traveldiary.domain.account.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TravelDiaryContext.class)
public class SqlMapperMakerTest {
    @Autowired
    SqlMapperMaker sqlMapperMaker;


    Account account01;
    Account account02;
    Account account03;

    AccountSampler accountSampler;

    @Before
    public void setUp() {
        assertNotNull(sqlMapperMaker);

        accountSampler = new AccountSampler();

        account01 = accountSampler.getRandomAccount();
        account02 = accountSampler.getRandomAccount();
        account03 = accountSampler.getRandomAccount();

    }

    @Test
    public void get() {
        Map<String, Object> getMap;
        Account testAccount;
        for (int i = 0; i < 100; i++) {
            testAccount = accountSampler.getRandomAccount();
            getMap = sqlMapperMaker.makeMapper(testAccount);
            confirmMap(getMap, testAccount);
        }
    }

    private void confirmMap(Map<String, Object> getMap, Object target) {
        try {
            for (Field field : target.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().equals("pk")) continue;
                if (!getMap.get(field.getName()).equals(field.get(target))) fail();
            }
        } catch (IllegalAccessException e) {
            fail();
        }
    }
}

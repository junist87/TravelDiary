package com.ciaosgarage.traveldiary.beans.dao.sqlMapperMakerTest;

import com.ciaosgarage.traveldiary.beans.dao.cryptor.Cryptor;
import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.beans.dao.sqlMapperMaker.SqlMapperMaker;
import com.ciaosgarage.traveldiary.context.DaoContext;
import com.ciaosgarage.traveldiary.domain.account.Account;
import com.ciaosgarage.traveldiary.sampler.AccountSampler;
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
@ContextConfiguration(classes = DaoContext.class)
public class SqlMapperMakerTest {
    @Autowired
    SqlMapperMaker sqlMapperMaker;


    @Autowired
    Cryptor cryptor;


    AccountSampler accountSampler;

    @Before
    public void setUp() {
        assertNotNull(sqlMapperMaker);

        accountSampler = new AccountSampler();

    }

    @Test
    public void getMapper() {
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

                Object value = getMap.get(field.getName());
                Object expectValue;

                expectValue = cryptor.encryption(target.getClass(), new ColumnValue(field.getName(), field.get(target)));
                if (!value.equals(expectValue)) fail();
            }
        } catch (IllegalAccessException e) {
            fail();
        }
    }
}

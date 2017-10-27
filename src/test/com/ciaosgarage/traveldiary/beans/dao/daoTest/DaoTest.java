package com.ciaosgarage.traveldiary.beans.dao.daoTest;


import com.ciaosgarage.traveldiary.beans.dao.dao.Dao;
import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.stateWhere.StateWhere;
import com.ciaosgarage.traveldiary.beans.dao.voHandler.VoHandler;
import com.ciaosgarage.traveldiary.context.DaoContext;
import com.ciaosgarage.traveldiary.beans.dao.dbSetter.DbTestSetter;
import com.ciaosgarage.traveldiary.sampler.AccountSampler;
import com.ciaosgarage.traveldiary.domain.account.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoContext.class)
public class DaoTest {
    @Autowired
    Dao dao;


    @Autowired
    VoHandler voHandler;

    AccountSampler accountSampler;

    DbTestSetter dbTestSetter;

    @Before
    public void setUp() {
        dbTestSetter = new DbTestSetter(dao);
        accountSampler = new AccountSampler();

        dbTestSetter.clearAllDbTable();
    }

    @Test
    public void addAndGet() {
        for (int i = 0; i < 100; i++) {
            Account account01 = accountSampler.getRandomAccount();
            dao.add(account01);
            List<ColumnValue> findingOption = Arrays.asList(new ColumnValue(voHandler.getPrimaryKeyColumnName(account01), voHandler.getPrimaryKey(account01)));

            Account getAccount = (Account) dao.get(Account.class, Arrays.asList(new StateWhere(findingOption)));
            checkVo(account01, getAccount);
            assertThat(dao.count(Account.class), is(i + 1));
        }
    }

    @Test
    public void updateAndGet() {

        for (int i = 0; i < 100; i++) {
            Account account = accountSampler.getRandomAccount();
            String pk = account.getPk();
            dao.add(account);
            Account editedAccount = accountSampler.getRandomAccount();
            editedAccount.setPk(pk);
            dao.update(editedAccount);

            List<ColumnValue> findingOption = Arrays.asList(new ColumnValue(voHandler.getPrimaryKeyColumnName(account), voHandler.getPrimaryKey(account)));
            Account getAccount = (Account) dao.get(Account.class, Arrays.asList(new StateWhere(findingOption)));
            checkVo(editedAccount, getAccount);
        }
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void delete() {
        dao.add(accountSampler.getRandomAccount());
        dao.add(accountSampler.getRandomAccount());
        dao.add(accountSampler.getRandomAccount());

        Account deleteAccount = accountSampler.getRandomAccount();
        dao.delete(deleteAccount);
        List<ColumnValue> findingOption = Arrays.asList(new ColumnValue(voHandler.getPrimaryKeyColumnName(deleteAccount), voHandler.getPrimaryKey(deleteAccount)));
        Account getAccount = (Account) dao.get(Account.class, Arrays.asList(new StateWhere(findingOption)));
    }

    private void printVo(Object vo) {
        try {
            for (Field field : vo.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                System.out.println(field.getName() + " : " + field.get(vo));
            }
        } catch (IllegalAccessException e) {
        }
    }
    private void checkVo(Account s1, Account s2) {
        try {
            for (Field field : s1.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getType().equals(java.sql.Date.class)) continue;
                assertThat(field.get(s1), is(field.get(s2)));
            }
        } catch (IllegalAccessException e) {
            fail();
            e.printStackTrace();
        }
    }
}

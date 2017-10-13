package com.ciaosgarage.traveldiary.dao.dao;


import com.ciaosgarage.traveldiary.sampler.AccountSampler;
import com.ciaosgarage.traveldiary.beans.dao.dao.Dao;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.stateWhere.StateWhere;
import com.ciaosgarage.traveldiary.beans.dao.voHandler.VoHandler;
import com.ciaosgarage.traveldiary.context.TravelDiaryContext;
import com.ciaosgarage.traveldiary.domain.account.Account;
import com.ciaosgarage.traveldiary.domain.mapBoard.MapBoard;
import com.ciaosgarage.traveldiary.domain.mapPhoto.MapPhoto;
import com.ciaosgarage.traveldiary.domain.seqTable.SeqTable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TravelDiaryContext.class)
public class DaoTest {
    @Autowired
    Dao dao;


    @Autowired
    VoHandler voHandler;

    AccountSampler accountSampler;

    @Before
    public void setUp() {
        dao.deleteAll(Account.class);
        dao.deleteAll(MapBoard.class);
        dao.deleteAll(MapPhoto.class);
        dao.deleteAll(SeqTable.class);
        accountSampler = new AccountSampler();
    }

    @Test
    public void addAndGet() {
        for (int i = 0; i < 100; i++) {
            Account account01 = accountSampler.getRandomAccount();
            dao.add(account01);
            checkVo(account01, (Account) dao.get(Account.class, Arrays.asList(new StateWhere(voHandler.getPrimaryKeyColumnName(account01), voHandler.getPrimaryKey(account01)))));
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
            checkVo(editedAccount, (Account) dao.get(Account.class, Arrays.asList(new StateWhere(voHandler.getPrimaryKeyColumnName(editedAccount), voHandler.getPrimaryKey(editedAccount)))));
        }
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void delete() {
        dao.add(accountSampler.getRandomAccount());
        dao.add(accountSampler.getRandomAccount());
        dao.add(accountSampler.getRandomAccount());

        Account deleteAccount = accountSampler.getRandomAccount();
        dao.delete(deleteAccount);
        dao.get(Account.class, Arrays.asList(new StateWhere(voHandler.getPrimaryKeyColumnName(deleteAccount), voHandler.getPrimaryKey(deleteAccount))));
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

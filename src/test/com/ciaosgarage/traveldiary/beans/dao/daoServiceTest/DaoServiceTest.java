package com.ciaosgarage.traveldiary.beans.dao.daoServiceTest;


import com.ciaosgarage.traveldiary.beans.dao.attachStatementHandler.AttachStatementHandler;
import com.ciaosgarage.traveldiary.context.BeansContext;
import com.ciaosgarage.traveldiary.sampler.AccountSampler;
import com.ciaosgarage.traveldiary.beans.dao.dao.Dao;
import com.ciaosgarage.traveldiary.domain.account.Account;
import com.ciaosgarage.traveldiary.testTools.DbTestSetter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeansContext.class)
public class DaoServiceTest {

    @Autowired
    DaoService daoService;

    @Autowired
    Dao dao;

    @Autowired
    AttachStatementHandler attachStatementHandler;

    DbTestSetter dbTestSetter;

    AccountSampler accountSampler;

    @Before
    public void setup() {
        dbTestSetter = new DbTestSetter(dao);
        accountSampler = new AccountSampler();
        dbTestSetter.clearAllDbTable();
        assertNotNull(daoService);
    }

    @Test
    public void push() {
        int tryNumber = 100;
        for (int i = 0; i < tryNumber; i++) {
            Account account = accountSampler.getRandomAccount();
            account.setPk(null);
            daoService.pushVo(account);
        }

        for (int i = 0; i < 50; i++) {
            // 만들어진 임의의 데이터 추출
            int getIndex = (int) (Math.random() * tryNumber);
            Account madeAccount = accountSampler.getMadeAccountList().get(getIndex);

            // 임의의 데이터를 추출
            attachStatementHandler.addColumnCondition("email", madeAccount.getEmail());
            Account getAccount = (Account) daoService.pullVo(Account.class);

            // 만들어진 데이터와 입력된 데이터가 같은지 조사
            isSameObject(getAccount, madeAccount);
        }
    }

    private void isSameObject(Account a1, Account a2) {
        assertThat(a1.getEmail(), is(a2.getEmail()));
        assertThat(a1.getNickname(), is(a2.getNickname()));
        assertThat(a1.getPassword(), is(a2.getPassword()));
    }
}

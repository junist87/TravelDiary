package com.ciaosgarage.traveldiary.dao.daoService;


import com.ciaosgarage.traveldiary.sampler.AccountSampler;
import com.ciaosgarage.traveldiary.beans.dao.dao.Dao;
import com.ciaosgarage.traveldiary.beans.dao.daoService.DaoService;
import com.ciaosgarage.traveldiary.context.TravelDiaryContext;
import com.ciaosgarage.traveldiary.domain.account.Account;
import com.ciaosgarage.traveldiary.domain.mapBoard.MapBoard;
import com.ciaosgarage.traveldiary.domain.mapPhoto.MapPhoto;
import com.ciaosgarage.traveldiary.domain.seqTable.SeqTable;
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
@ContextConfiguration(classes = TravelDiaryContext.class)
public class DaoServiceTest {

    @Autowired
    DaoService daoService;

    @Autowired
    Dao dao;

    AccountSampler accountSampler;

    @Before
    public void setup() {
        dao.deleteAll(Account.class);
        dao.deleteAll(MapBoard.class);
        dao.deleteAll(MapPhoto.class);
        dao.deleteAll(SeqTable.class);
        assertNotNull(daoService);
        accountSampler = new AccountSampler();
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
            daoService.setColumnCondition("email", madeAccount.getEmail());
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

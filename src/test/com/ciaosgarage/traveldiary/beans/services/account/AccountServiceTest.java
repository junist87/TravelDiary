package com.ciaosgarage.traveldiary.beans.services.account;

import com.ciaosgarage.traveldiary.beans.dao.dao.Dao;
import com.ciaosgarage.traveldiary.beans.services.AccountService;
import com.ciaosgarage.traveldiary.beans.services.LoginStatus;
import com.ciaosgarage.traveldiary.context.BeansContext;
import com.ciaosgarage.traveldiary.domain.account.Account;
import com.ciaosgarage.traveldiary.sampler.AccountSampler;
import com.ciaosgarage.traveldiary.testTools.DbTestSetter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeansContext.class)
public class AccountServiceTest {

    @Autowired
    Dao dao;

    @Autowired
    AccountService accountService;

    DbTestSetter dbTestSetter;
    AccountSampler accountSampler;

    @Before
    public void setup() {
        assertNotNull(accountService);
        assertNotNull(dao);

        dbTestSetter = new DbTestSetter(dao);
        accountSampler = new AccountSampler();
        dbTestSetter.clearAllDbTable();
    }

    @Test
    public void addNewAndGetAccount() {
        for (int i = 0; i < 50; i++) {
            Account account = accountSampler.getRandomAccount();
            accountService.addNew(account.getEmail(), account.getPassword(), account.getNickname());
            String unlockKey = accountService.lockAccount(account.getEmail());
            account.setAuthorizationKey(unlockKey);

            Account getAccount = accountService.getAccount(account.getEmail());
            accountSampler.assertAccountWithoutPk(account, getAccount);
        }
    }

    @Test
    public void loginTest() {
        for (int i = 0; i < 50; i++) {
            Account account = accountSampler.getRandomAccount();

            // 등록된 어카운트가 없을때
            assertThat(accountService.login(account.getEmail(), account.getPassword()), is(LoginStatus.INVALIDACCOUNT));

            // 새로운 어카운트 등록
            accountService.addNew(account.getEmail(), account.getPassword(), account.getNickname());

            // 비밀번호가 틀렸을때
            assertThat(accountService.login(account.getEmail(), "daf3"), is(LoginStatus.WORNGPASSWORD));

            // 인증키를 입력하지 않았을 때
            assertThat(accountService.login(account.getEmail(), account.getPassword()), is(LoginStatus.LOCK));

            // 인증키 입력
            String unlockKey = accountService.lockAccount(account.getEmail());
            accountService.unlockAccount(account.getEmail(), account.getPassword(), unlockKey);

            // 인증키 입력 후 정상로그인 확인
            assertThat(accountService.login(account.getEmail(), account.getPassword()), is(LoginStatus.PASS));
        }
    }


    @Test
    public void edit() {
        for (int i = 0; i < 50; i++) {
            Account account = accountSampler.getRandomAccount();
            accountService.addNew(account.getEmail(), account.getPassword(), account.getNickname());

            Account editedAccount = accountSampler.getRandomAccount();

            accountService.edit(account.getEmail(), account.getPassword(), editedAccount.getPassword(), editedAccount.getNickname());
            Account getAccount = accountService.getAccount(account.getEmail());

            assertThat(editedAccount.getPassword(), is(getAccount.getPassword()));
            assertThat(editedAccount.getNickname(), is(getAccount.getNickname()));
        }


    }
}

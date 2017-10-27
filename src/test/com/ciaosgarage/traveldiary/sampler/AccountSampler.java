package com.ciaosgarage.traveldiary.sampler;

import com.ciaosgarage.traveldiary.domain.account.Account;
import com.ciaosgarage.traveldiary.domain.account.AccountAutholization;
import com.ciaosgarage.traveldiary.testTools.VoSampler;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class AccountSampler {
    List<Account> madeAccountList = new ArrayList<>();
    VoSampler samplerTool = new VoSampler();
    int index = 0;

    public Account getRandomAccount() {
        Account account = new Account();

        account.setPk(Long.toHexString(index));
        account.setAuthorization(AccountAutholization.LOCK);
        account.setEmail(samplerTool.getMail());
        account.setNickname(samplerTool.getStringDeniedDuplicated(10));
        account.setPassword(samplerTool.getStringDeniedDuplicated(10));
        account.setAuthorizationKey(samplerTool.getStringDeniedDuplicated(10));

        madeAccountList.add(account);
        index += 1;
        return account;
    }

    public List<Account> getMadeAccountList() {
        return madeAccountList;
    }

    public void assertAccountWithoutPk(Account a1, Account a2) {
        try {
            for (Field field : a1.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getType().equals(Date.class)) continue;
                if (field.getName().equals("pk")) continue;

                System.out.println("Assert[" + field.getName() + "] - " + field.get(a1) + " : " + field.get(a2));
                assertThat(field.get(a1), is(field.get(a2)));
            }
        } catch (IllegalAccessException e) {
            fail();
        }
    }
}

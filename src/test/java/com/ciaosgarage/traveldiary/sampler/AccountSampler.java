package com.ciaosgarage.traveldiary.sampler;

import com.ciaosgarage.traveldiary.domain.account.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountSampler {

    List<Account> madeAccountList = new ArrayList<>();
    VoSamplerTool samplerTool = new VoSamplerTool();
    int index = 0;

    public Account getRandomAccount() {
        Account account = new Account();

        account.setPk(Long.toHexString(index));
        account.setAuthorization(0);
        account.setEmail(samplerTool.getMail());
        account.setNickname(samplerTool.getStringDeniedDuplicated(10));
        account.setPassword(samplerTool.getStringDeniedDuplicated(10));

        madeAccountList.add(account);
        index += 1;
        return account;
    }

    public List<Account> getMadeAccountList() {
        return madeAccountList;
    }
}

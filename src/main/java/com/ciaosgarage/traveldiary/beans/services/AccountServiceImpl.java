package com.ciaosgarage.traveldiary.beans.services;

import com.ciaosgarage.traveldiary.beans.dao.attachStatementHandler.AttachStatementHandler;
import com.ciaosgarage.traveldiary.beans.dao.daoServiceTest.DaoService;
import com.ciaosgarage.traveldiary.beans.mailSender.MailSender;
import com.ciaosgarage.traveldiary.beans.textReader.TextReader;
import com.ciaosgarage.traveldiary.domain.account.Account;
import com.ciaosgarage.traveldiary.domain.account.AccountAutholization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    DaoService daoService;

    @Autowired
    AttachStatementHandler statementHandler;

    @Autowired
    MailSender mailSender;

    @Autowired
    TextReader textReader;

    @Override
    public void addNew(String email, String password, String nickname) throws DuplicatedEmailException {
        // 중복 이메일 검사
        if (isEmailDuplicated(email)) throw new DuplicatedEmailException("email : " + email);

        // 새로운 계정정보를 만든후 데이터베이스에 입력한다.
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        account.setNickname(nickname);
        daoService.pushVo(account);

        // 계정을 잠근다
        this.lockAccount(email);
    }

    private boolean isEmailDuplicated(String email) {
        try {
            this.getAccount(email);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public Account getAccount(String email) throws EmptyResultDataAccessException {
        statementHandler.addColumnCondition("email", email);
        return (Account) daoService.pullVo(Account.class);
    }

    @Override
    public LoginStatus login(String email, String password) {
        try {
            Account account = this.getAccount(email);
            if (!account.getPassword().equals(password)) return LoginStatus.WORNGPASSWORD;
            if (account.getAuthorization().equals(AccountAutholization.LOCK)) return LoginStatus.LOCK;
            return LoginStatus.PASS;
        } catch (EmptyResultDataAccessException e) {
            return LoginStatus.INVALIDACCOUNT;
        }
    }

    @Override
    public void edit(String email, String oldPassword, String newPassword, String nickname)
            throws EmptyResultDataAccessException, InvalidPasswordException {
        // 기존의 값을 불러온 뒤, 암호확인을 한다.
        Account account = this.getAccount(email);   // 값이 존재하지 않으면 EmptyResultDataAccessException 발생
        if (!account.getPassword().equals(oldPassword))
            throw new InvalidPasswordException(); // 비밀번호가 틀리면 InvalidPasswordException 발생

        // 변경된 값을 저장한다.
        account.setPassword(newPassword);
        account.setNickname(nickname);
        daoService.pushVo(account);
    }

    @Override
    public void unlockAccount(String email, String password, String unlockKey)
            throws EmptyResultDataAccessException, InvalidPasswordException, InvalidAuthorizationKeyException {
        // 기존 값을 불러온 뒤, 비밀번호와 언락키를 비교한다.
        Account account = this.getAccount(email);   // 값이 존재하지 않으면 EmptyResultDataAccessException 발생
        if (!account.getPassword().equals(password))
            throw new InvalidPasswordException(); // 비밀번호가 틀리면 InvalidPasswordException 발생
        if (!account.getAuthorizationKey().equals(unlockKey))
            throw new InvalidAuthorizationKeyException(); // 인증키가 틀리면 InvalidAuthorizationKeyException 발생

        // 인증이 완료 되었으므로 그 값을 저장한다.
        account.setAuthorization(AccountAutholization.UNLOCK);
        daoService.pushVo(account);
    }

    @Override
    public String lockAccount(String email) throws EmptyResultDataAccessException {
        // 어카운트 정보를 가져온뒤, 새로운 인증키를 입력한다.
        Account getAccount = this.getAccount(email);  // 값이 존재하지 않으면 EmptyResultDataAccessException 발생
        String key = getNewAuthorizationKey();
        getAccount.setAuthorizationKey(key);
        daoService.pushVo(getAccount);

        // 인증키를 메일로 전송한다.
        sendAuthKeyToEmail(email, key);

        // 인증키를 리턴한다
        return key;
    }

    private void sendAuthKeyToEmail(String targetEmail, String key) {
        try {
            String title = "TravelDiary 인증키 안내";
            String context = textReader.read(new File(".").getCanonicalPath() + "/src/resources/AccountAuthorization");
            context += "<p>" + key + "</p>";
            String senderEmail = "";
            mailSender.sendMail(title, context, senderEmail, targetEmail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getNewAuthorizationKey() {
        return getString(10);
    }

    private String getChar() {
        // 대문자 A 부터 Z 사이의 값이 나온다
        int decChar = (int) (Math.random() * 25) + 65;
        return String.valueOf((char) decChar);
    }

    private String getString(int length) {
        StringBuffer chars = new StringBuffer();
        for (int i = 0; i < length; i++) {
            chars.append(getChar());
        }
        return chars.toString();
    }
}

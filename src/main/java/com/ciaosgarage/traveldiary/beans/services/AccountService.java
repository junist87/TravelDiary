package com.ciaosgarage.traveldiary.beans.services;

import com.ciaosgarage.traveldiary.domain.account.Account;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * 계정에 관련한 모든 기능을 담당하는 클래스
 */
public interface AccountService {
    /**
     * 새로운 계정을 만든다.
     *
     * @param email    the email
     * @param password the password
     * @param nickname the nickname
     * @throws DuplicatedEmailException the duplicated email exception
     */
    void addNew(String email, String password, String nickname) throws DuplicatedEmailException;

    /**
     * Gets account.
     *
     * @param email the email
     * @return the account
     * @throws EmptyResultDataAccessException the empty result data access exception
     */
    Account getAccount(String email)throws EmptyResultDataAccessException;

    /**
     * Login login status.
     *
     * @param email    the email
     * @param password the password
     * @return the login status
     */
    LoginStatus login(String email, String password);

    /**
     * Edit.
     *
     * @param email       the email
     * @param oldPassword the old password
     * @param newPassword the new password
     * @param nickname    the nickname
     * @throws EmptyResultDataAccessException the empty result data access exception
     * @throws InvalidPasswordException       the invalid password exception
     */
    void edit(String email, String oldPassword, String newPassword, String nickname)throws EmptyResultDataAccessException, InvalidPasswordException ;

    /**
     * Unlock account.
     *
     * @param email     the email
     * @param password  the password
     * @param unlockKey the unlock key
     * @throws EmptyResultDataAccessException   the empty result data access exception
     * @throws InvalidPasswordException         the invalid password exception
     * @throws InvalidAuthorizationKeyException the invalid authorization key exception
     */
    void unlockAccount(String email, String password, String unlockKey)throws EmptyResultDataAccessException, InvalidPasswordException, InvalidAuthorizationKeyException ;

    /**
     * Lock account.
     *
     * @param email the email
     * @return the string
     * @throws EmptyResultDataAccessException the empty result data access exception
     */
    String lockAccount(String email) throws EmptyResultDataAccessException;
}

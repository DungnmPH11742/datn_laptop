package com.poly.service;

import com.poly.entity.Account;
import com.poly.entity.AuthenticationProvider;
import com.poly.vo.AccountVO;
import com.poly.vo.ProductsVO;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.security.auth.login.AccountNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public interface AccountService {
    List<AccountVO> getList();

    Account findByEmail(String email);

    Account createAccountUser(AccountVO accountVO);

    AccountVO findAccountById(Integer id);

    public AccountVO updateAccount(AccountVO accountVO);

    void createAccountByRegister(AccountVO accountVO, String siteURL) throws UnsupportedEncodingException, MessagingException, MessagingException;

    Account updateAccountUser(AccountVO accountVO);

    boolean verify(String code);

    void deleteAccountById(Integer id);

    void updateAccountByRegister(AccountVO accountVO, String siteURL) throws UnsupportedEncodingException, MessagingException;

    AccountVO findByEmailUser(String email);

    AccountVO findByEmailVO(String email);

    Account findUserAccountByEmailFalse(String email);

    Account findUserAccountByToken(String token);

    public void createNewCustomerAfterOAuthLoginSuccess(String email, String name, AuthenticationProvider provider);

    public void upadteCustomerAfterOAuthLoginSuccess(Account account, String name, AuthenticationProvider provider);

    void updateResetPasswordToken(String token, String email) throws AccountNotFoundException;

    Account getByResetPasswordToken(String token);

    void updatePassword(Account account, String newPassword);

    void sendForgotPasswordEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;

}

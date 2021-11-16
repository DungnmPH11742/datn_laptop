package com.poly.service;

import com.poly.entity.Account;
import com.poly.entity.AuthenticationProvider;
import com.poly.vo.AccountVO;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public interface AccountService {
    List<AccountVO> getList();

    Account createAccountUser(AccountVO accountVO);

    void createAccountByRegister(AccountVO accountVO, String siteURL) throws UnsupportedEncodingException, MessagingException;

    Account updateAccountUser(AccountVO accountVO);

    boolean verify(String code);

    void deleteAccountById(Integer id);

    void updateAccountByRegister(AccountVO accountVO, String siteURL) throws UnsupportedEncodingException, MessagingException;

    Account findByEmail(String email);

    Account findUserAccountByEmailFalse(String email);

    Account findUserAccountByToken(String token);

    public void createNewCustomerAfterOAuthLoginSuccess(String email, String name, AuthenticationProvider provider);

    public void upadteCustomerAfterOAuthLoginSuccess(Account account, String name, AuthenticationProvider provider);
}

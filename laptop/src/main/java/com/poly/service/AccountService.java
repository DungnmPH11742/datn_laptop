package com.poly.service;

import com.poly.entity.Account;
import com.poly.vo.AccountVO;
import com.poly.vo.ProductsVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    List<AccountVO> getList();

    Account createAccountUser(AccountVO accountVO);

    Account updateAccountUser(AccountVO accountVO);

    void deleteAccountById(Integer id);

    Account findByEmail(String email);
}
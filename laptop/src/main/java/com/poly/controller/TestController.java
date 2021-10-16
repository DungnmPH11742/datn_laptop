package com.poly.controller;

import com.poly.entity.Account;
import com.poly.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/list-account")
    public List<Account> getList(){
        return accountRepository.findAll();
    }

}

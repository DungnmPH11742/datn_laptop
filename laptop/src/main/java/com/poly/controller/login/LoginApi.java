package com.poly.controller.login;

import com.poly.repo.AccountRepository;
import com.poly.service.AccountService;
import com.poly.validate.AccountValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:4200/")
public class LoginApi {
    @Autowired
    private AccountService service;

    @Autowired
    private AccountValidate validate;

    @PostMapping("/auth")
    public void login(@RequestParam("email") String email, @RequestParam("pass") String pass) {

    }
}

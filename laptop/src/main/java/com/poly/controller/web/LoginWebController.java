package com.poly.controller.web;

import com.poly.entity.Account;
import com.poly.repo.AccountRepository;
import com.poly.service.AccountService;
import com.poly.validate.AccountValidate;
import com.poly.vo.AccountVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Controller
public class LoginWebController {
    @Autowired
    private AccountService service;
    @Autowired
    private AccountRepository repository;
    @Autowired
    private AccountValidate validate;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "user/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("account", new AccountVO());
        return "user/register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("account") @Valid AccountVO account, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model)
            throws UnsupportedEncodingException, MessagingException {
        validate.validate(account, bindingResult);
        if (bindingResult.hasErrors()) {
            return "user/register";
        }
        Account accountRegister = service.findUserAccountByEmailFalse(account.getEmail());
        if (accountRegister != null) {
            Integer id = accountRegister.getId();
            account.setId(id);
            service.updateAccountByRegister(account, getSiteURL(request));
        } else {
            service.createAccountByRegister(account, getSiteURL(request));
        }

        return "user/register_success";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("token") String token, WebRequest request, Model model) {
        if (service.verify(token)) {
            return "user/verify_success";
        } else {
            return "user/verify_fail";
        }
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}

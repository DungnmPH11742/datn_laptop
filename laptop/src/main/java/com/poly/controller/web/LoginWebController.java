package com.poly.controller.web;

import com.poly.entity.Account;
import com.poly.repo.AccountRepository;
import com.poly.service.AccountService;
import com.poly.validate.AccountValidate;
import com.poly.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
public class LoginWebController {
    @Autowired
    private AccountService accountService;
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
        Account accountRegister = accountService.findUserAccountByEmailFalse(account.getEmail());
        if (accountRegister != null) {
            Integer id = accountRegister.getId();
            account.setId(id);
            accountService.updateAccountByRegister(account, getSiteURL(request));
        } else {
            accountService.createAccountByRegister(account, getSiteURL(request));
        }

        return "user/register_success";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("token") String token, WebRequest request, Model model) {
        if (accountService.verify(token)) {
            return "user/verify_success";
        } else {
            return "user/verify_fail";
        }
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Account account = accountService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (account == null) {
            model.addAttribute("message", "Mã không hợp lệ vui lòng kiểm tra lại đường dẫn trong email của bạn");
            return "user/reset_password_form";
        }

        return "user/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        Account account = accountService.getByResetPasswordToken(token);
        if (account == null) {
            model.addAttribute("message", "Mã không hợp lệ vui lòng kiểm tra lại đường dẫn trong email của bạn");
            return "user/reset_password_form";
        } else {
            accountService.updatePassword(account, password);
            model.addAttribute("messageSuccess", "Bạn đã thay đổi thành công mật khẩu của bạn.");
            return "user/login";
        }
    }
}
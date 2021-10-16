package com.poly.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginWebController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "user/login";
    }

    // khi người dùng đăng nhập thành công
    @GetMapping("/home")
    public String userInfo() {
        return "user/index";
    }


    // khi người dùng đăng nhập thành công
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}

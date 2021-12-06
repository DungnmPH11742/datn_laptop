package com.poly.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginWebController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "user/login";
    }

//Cate parent_id == null
//Cate parent_id == id bên trên

    // khi người dùng đăng nhập thành công
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }


}

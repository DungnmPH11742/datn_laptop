package com.poly.controller.web;

import com.poly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginWebController {
    @Autowired
    private ProductService productService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "user/login";
    }

    // khi người dùng đăng nhập thành công
    @GetMapping("/home")
    public String userInfo(Model model) {
        model.addAttribute("list_product", productService.getListForCate(76));
        return "user/index";
    }


    // khi người dùng đăng nhập thành công
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}

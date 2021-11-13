package com.poly.controller.web;

import com.poly.helper.HeaderHelper;
import com.poly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeWebController {
    @Autowired
    private ProductService productService;

    @Autowired
    private HeaderHelper headerHelper;

    @GetMapping("/home")
    public String userInfo(Model model) {
        model.addAttribute("list_product", productService.getList());
        model.addAttribute("list_sale", productService.getListByCodeSale("SL001"));
        headerHelper.setHeaderSession(model);
        return "user/index";
    }
}

package com.poly.controller.web;

import com.poly.service.CategoryService;
import com.poly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeWebController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/home")
    public String userInfo(Model model) {
        model.addAttribute("list_product", productService.getList());
        model.addAttribute("cate_all", categoryService.getList());
        model.addAttribute("cate_lt", categoryService.getListByParent(1));
        model.addAttribute("cate_pc", categoryService.getListByParent(57));
        model.addAttribute("cate_mo", categoryService.getListByParent(76));
        model.addAttribute("list_sale", productService.getListByCodeSale("SL001"));
        return "user/index";
    }
}

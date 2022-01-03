package com.poly.controller.web;

import com.poly.helper.HeaderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoryWebController {

    @Autowired
    private HeaderHelper headerHelper;


    @GetMapping("/category/{nameBrand}")
    public String getBrandCate(Model model, @PathVariable("nameBrand") String nameBrand) {
        headerHelper.setHeaderSession(model);
        return "user/duong-test";
    }
    @GetMapping("/categorySearch")
    public String getSearch(Model model) {
        headerHelper.setHeaderSession(model);
        return "user/duong-test2";
    }
}
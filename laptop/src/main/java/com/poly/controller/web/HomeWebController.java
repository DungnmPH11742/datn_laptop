package com.poly.controller.web;

import com.poly.helper.HeaderHelper;
import com.poly.service.ProductDetailService;
import com.poly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeWebController {
    @Autowired
    private ProductService productService;

    @Autowired
    private HeaderHelper headerHelper;

    @Autowired
    private ProductDetailService productDetailService;

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/home")
    public String userInfo(Model model) {
        model.addAttribute("list_product", productService.findAllSku());
        model.addAttribute("list_sale", productService.getListByCodeSale("SL001"));
        headerHelper.setHeaderSession(model);
        ResponseEntity<Object[]> result
                = restTemplate.postForEntity("http://localhost:8080/ajax/districts-address?code=2", "", Object[].class);
        return "user/index";
    }
}

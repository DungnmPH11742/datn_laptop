package com.poly.controller.web;

import com.poly.helper.HeaderHelper;
import com.poly.service.ProductService;
import com.poly.vo.ProductsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProductDetailController {

    @Autowired
    private HeaderHelper headerHelper;

    @Autowired
    private ProductService productService;

    @RequestMapping("/view")
    public String viewProduct(@RequestParam("id") String id, Model model) {
        ProductsVO productsVO = productService.getOne(id);
        headerHelper.setHeaderSession(model);
        model.addAttribute("related_products", productService.getListByCate(productsVO.getCategory().getParentId()));
        model.addAttribute("product", productService.getOne(id));
        return "user/product-details";
    }

}

package com.poly.controller.web;

import com.poly.helper.HeaderHelper;
import com.poly.service.AccountService;
import com.poly.service.DescriptionService;
import com.poly.service.ProductDetailService;
import com.poly.service.ProductService;
import com.poly.vo.ProductsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ProductDetailController {

    @Autowired
    private HeaderHelper headerHelper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private DescriptionService descriptionService;

    @Autowired
    private AccountService accountService;

    @ModelAttribute("user")
    public String loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email ;
        if (auth == null || auth.getPrincipal() == "anonymousUser") {
            email =  null;
        }
        else{
            email = accountService.findByEmailUser(auth.getName()).getFullName();
        }
        return email;
    }
    @ModelAttribute("img")
    public String loggedInUserIMG() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String img ;
        if (auth == null || auth.getPrincipal() == "anonymousUser") {
            img =  null;
        }
        else{
            img = accountService.findByEmailUser(auth.getName()).getImgUrl();
        }
        return img;
    }

    @RequestMapping("/view/{id}")
    public String viewProduct(@PathVariable("id") String id, @RequestParam("sku") String sku, Model model) {
        ProductsVO productsVO = productService.getOne(id);
        headerHelper.setHeaderSession(model);
        model.addAttribute("related_products", productService.getListByCate(productsVO.getCategory().getParentId()));
        model.addAttribute("product", productService.getOne(id));
        model.addAttribute("description_p", descriptionService.getDescriptionBySku(sku));
        model.addAttribute("pd", productDetailService.findBySku(sku));
        return "user/product-details";
    }

}

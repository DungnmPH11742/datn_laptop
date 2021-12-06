package com.poly.controller.web;

import com.poly.service.CategoryService;
import com.poly.service.ProductService;
import com.poly.service.VouchersService;
import com.poly.vo.ProductsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductDetailController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;



    @RequestMapping("/view")
    public String viewProduct(@RequestParam("id") String id, Model model){
        ProductsVO productsVO = productService.getOne(id);
        model.addAttribute("cate_all", categoryService.getList());
        model.addAttribute("cate_lt", categoryService.getListByParent(1));
        model.addAttribute("cate_pc", categoryService.getListByParent(57));
        model.addAttribute("cate_mo", categoryService.getListByParent(76));
        model.addAttribute("product", productsVO);
        model.addAttribute("related_products", productService.getListByCate(productsVO.getCategory().getParentId()));
        System.out.println(productService.getListByCate(productsVO.getCategory().getParentId()).size());
        return "user/product-details";
    }




}

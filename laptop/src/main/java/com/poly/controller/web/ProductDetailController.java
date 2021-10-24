package com.poly.controller.web;

import com.poly.entity.Category;
import com.poly.service.CategoryService;
import com.poly.service.ProductService;
import com.poly.vo.CategoryVO;
import com.poly.vo.ProductsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductDetailController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @RequestMapping("/view")
    public String viewProduct(@RequestParam("id") String id, Model model){
        model.addAttribute("cate_all", categoryService.getList());
        model.addAttribute("cate_lt", categoryService.getListByParent(1));
        model.addAttribute("cate_pc", categoryService.getListByParent(57));
        model.addAttribute("cate_mo", categoryService.getListByParent(76));
        model.addAttribute("product", productService.getOne(id));
        return "user/product-details";
    }
}

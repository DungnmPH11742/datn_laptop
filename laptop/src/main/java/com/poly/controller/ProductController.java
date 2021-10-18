package com.poly.controller;

import com.poly.service.ProductService;
import com.poly.vo.ProductsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/list-ps")
    public List<ProductsVO> getList(){
        return productService.getListForCate(1);
    }
}

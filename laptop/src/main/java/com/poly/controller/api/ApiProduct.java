package com.poly.controller.api;

import com.poly.service.ProductDetailService;
import com.poly.service.ProductService;
import com.poly.vo.ProductsDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api-product")
@RestController
public class ApiProduct {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;

//    @GetMapping("/findName")
//    public List<ProductsVO> products(@RequestParam(name = "name",defaultValue = "0") String name) {
//        return productService.findAllByNameLikeHome(name);
//    }
    @GetMapping("/findName")
    public List<ProductsDetailVO> products(@RequestParam(name = "name",defaultValue = "") String name) {

        return productDetailService.findAllByNameLikeHome(name);
    }
}

package com.poly.controller.api;

import com.poly.service.ProductService;
import com.poly.vo.ProductsVO;
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

    @GetMapping("/findName")
    public List<ProductsVO> products(@RequestParam(name = "name",defaultValue = "0") String name) {
        return productService.findAllByNameLikeHome(name);
    }

}

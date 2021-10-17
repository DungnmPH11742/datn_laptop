package com.poly.service;

import com.poly.vo.ProductsVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ProductService {
    List<ProductsVO> getList();

    ProductsVO create();
}

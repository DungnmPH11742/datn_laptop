package com.poly.service;

import com.poly.vo.ProductsVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductsVO> getList();

    ProductsVO create();
}

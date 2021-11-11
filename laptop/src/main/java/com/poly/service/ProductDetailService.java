package com.poly.service;

import com.poly.vo.ProductsDetailVO;
import com.poly.vo.ProductsVO;

import java.util.List;

public interface ProductDetailService {
    List<ProductsDetailVO> getList();

    ProductsDetailVO getOne(String id);

    ProductsDetailVO create(ProductsDetailVO vo);

    ProductsDetailVO update(ProductsDetailVO vo);

    boolean delete(String id);

}

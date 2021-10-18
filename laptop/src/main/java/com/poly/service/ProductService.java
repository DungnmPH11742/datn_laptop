package com.poly.service;

import com.poly.vo.ProductsVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductsVO> getList();

    ProductsVO getOne(String id);

    ProductsVO create(ProductsVO vo);

    ProductsVO update(ProductsVO vo);

    void delete(String id);

    List<ProductsVO> getListPage(Optional<Integer> page, Optional<Integer> row);

    List<ProductsVO> getListForCate(Integer id);
}

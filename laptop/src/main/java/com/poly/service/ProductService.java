package com.poly.service;

import com.poly.vo.ProductsVO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductsVO> getList();

    ProductsVO getOne(String id);

    ProductsVO create(ProductsVO vo);

    ProductsVO update(ProductsVO vo);

    boolean delete(String id);
    List<ProductsVO> findByNameContainingAndTypeOfItem(String name, int type);
    List<ProductsVO> getListPage(Optional<Integer> page, Optional<Integer> row);

    List<ProductsVO> getListByCate(Integer id);

    List<ProductsVO> getListByCodeSale(String code);
}

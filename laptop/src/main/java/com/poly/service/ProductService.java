package com.poly.service;

import com.poly.entity.Products;
import com.poly.filter.ProductSearchCriteria;
import com.poly.vo.ProductsVO;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductsVO> getList();

    ProductsVO getOne(String id);

    List<ProductsVO> findByNameContainingAndTypeOfItem(String name, int type);

    ProductsVO create(ProductsVO vo);

    ProductsVO update(ProductsVO vo);

    void delete(String id);

    List<ProductsVO> getListPage(Optional<Integer> page, Optional<Integer> row);

    List<ProductsVO> getListByCate(Integer id);

    List<ProductsVO> getListByCodeSale(String code);

    Page<Products> getListByPageNumber(int page, int limit, List<Products> lstProducts, String sortPrice);

    List<Products> retrieveProducts(ProductSearchCriteria searchCriteria);

    Page<Products> findAllByNameLike(int page, int limit, String name, Integer cateParent);
}

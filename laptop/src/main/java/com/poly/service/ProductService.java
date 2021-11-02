package com.poly.service;

import com.poly.entity.Products;
import com.poly.vo.ProductsVO;
import com.poly.vo.response.SanPhamFilter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductsVO> getList();
    //Page<Products> getListByPageNumber(SanPhamFilter filter, int page, int limit) ;
    ProductsVO getOne(String id);

    ProductsVO create(ProductsVO vo);

    ProductsVO update(ProductsVO vo);

    void delete(String id);

    List<ProductsVO> getListPage(Optional<Integer> page, Optional<Integer> row);

    List<ProductsVO> getListByCate(Integer id);

    List<ProductsVO> getListByCodeSale(String code);
    Page<Products> getListByPageNumber( int page,int limit, List<Products> lstProducts);
}

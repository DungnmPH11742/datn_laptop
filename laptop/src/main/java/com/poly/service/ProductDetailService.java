package com.poly.service;

import com.poly.vo.ProductsDetailVO;
import com.poly.vo.response.ProductsReponseVO;

public interface ProductDetailService {

    ProductsReponseVO findBySkuProduct(String sku);

    ProductsDetailVO findBySku(String sku);

    ProductsDetailVO save(ProductsDetailVO vo);

    boolean delete(String sku);
}

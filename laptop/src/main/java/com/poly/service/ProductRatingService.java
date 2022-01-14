package com.poly.service;


import com.poly.entity.ProductRating;
import com.poly.vo.ProductRatingVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductRatingService {
    ProductRatingVO getOne(Integer id);

    List<ProductRatingVO> findAllByProductsDetail_Sku(String id);

    ProductRating findProductRatingByAccountAndProductDetail(String email, String sku);

    ProductRatingVO create(ProductRatingVO vo);

    ProductRatingVO update(ProductRatingVO vo);

    Page<ProductRatingVO> getAllProductRatingVOByPageNumber(int page, int limit, String id);
}

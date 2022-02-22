package com.poly.repo;

import com.poly.entity.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRatingRepository extends JpaRepository<ProductRating, Integer> {
    ProductRating findByAccount_EmailAndProductsDetail_Sku(String email, String sku);

    List<ProductRating> findAllByProductsDetail_Sku(String id);

}
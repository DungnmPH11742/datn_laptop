package com.poly.repo;

import com.poly.entity.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRatingRepository extends JpaRepository<ProductRating, Integer> {
    ProductRating findByAccount_EmailAndProduct_Id(String email,String productId);
    List<ProductRating> findAllByProduct_Id(String id);

}
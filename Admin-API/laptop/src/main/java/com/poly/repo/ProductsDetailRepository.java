package com.poly.repo;

import com.poly.entity.ProductsDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ProductsDetailRepository extends JpaRepository<ProductsDetail, String>, JpaSpecificationExecutor<ProductsDetail> {

}
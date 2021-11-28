package com.poly.repo;

import com.poly.entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface SaleProductRepository extends JpaRepository<SaleProduct, String>, JpaSpecificationExecutor<SaleProduct> {

}
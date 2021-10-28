package com.poly.repo;

import com.poly.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ProductsRepository extends JpaRepository<Products, String>, JpaSpecificationExecutor<Products> {

}
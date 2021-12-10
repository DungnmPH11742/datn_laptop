package com.poly.repo;

import com.poly.entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableJpaRepositories
public interface SaleProductRepository extends JpaRepository<SaleProduct, String>, JpaSpecificationExecutor<SaleProduct> {
    @Query("select s from SaleProduct s where s.status != -1")
    List<SaleProduct> getAllSale();

    @Transactional
    @Modifying
    @Query("update SaleProduct s set s.status=-1 where s.saleCode=?1")
    void updateStatusSale(String saleCode);

}
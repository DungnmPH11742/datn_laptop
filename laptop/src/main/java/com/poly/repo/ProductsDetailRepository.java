package com.poly.repo;

import com.poly.entity.ProductsDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface ProductsDetailRepository extends JpaRepository<ProductsDetail, String>, JpaSpecificationExecutor<ProductsDetail> {
    ProductsDetail findBySkuAndStatusNot(String sku, int status);

    List<ProductsDetail> findAllByProduct_Category_IdOrProduct_Category_Id(String idCate, String idCate2);

    List<ProductsDetail> findAllByProduct_TypeOfItemAndProduct_Category_ParentId(String type, String pantId);

    List<ProductsDetail> findAllByProduct_TypeOfItemAndProduct_Category_Id(String type, String idCate);

    List<ProductsDetail> findAllByProduct_TypeOfItem(String id);

    List<ProductsDetail> findAllByProduct_Category_Id(String id);

    List<ProductsDetail> findAllByProduct_NameIgnoreCaseContainingAndStatusEquals(String name, Integer status);

    List<ProductsDetail> findAllByProduct_NameIgnoreCaseContainingAndProduct_TypeOfItemIgnoreCaseContaining(String name, String type);
}
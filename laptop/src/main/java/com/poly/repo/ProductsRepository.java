package com.poly.repo;

import com.poly.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface ProductsRepository extends JpaRepository<Products, String>, JpaSpecificationExecutor<Products> {

    @Query(value="SELECT * FROM ( SELECT * , ROW_NUMBER() OVER (ORDER BY id) AS RowNum FROM products  where type_of_item =?1) AS MyDerivedTable WHERE MyDerivedTable.RowNum BETWEEN 1 AND 12",
            nativeQuery = true)
    List<Products> getListForCate(Integer idCate);
}
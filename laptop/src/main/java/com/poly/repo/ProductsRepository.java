package com.poly.repo;

import com.poly.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;

@EnableJpaRepositories
public interface ProductsRepository extends JpaRepository<Products, String>, JpaSpecificationExecutor<Products> {

    @Query(value = "SELECT * FROM ( SELECT * , ROW_NUMBER() OVER (ORDER BY id) AS RowNum FROM products  where type_of_item =?1) AS MyDerivedTable WHERE MyDerivedTable.RowNum BETWEEN 1 AND 12",
            nativeQuery = true)
    List<Products> getListByCate(Integer idCate);

    @Query("SELECT m FROM Products m WHERE m.name LIKE %:name%")
    List<Products> findAllByNameLike(@Param("name") String name);

    @Query("select p from Products p where p.saleProduct.saleCode=:code")
    List<Products> getListByCodeSale(@Param("code") String code);

    List<Products> findAllByCategory_IdOrCategory_Id(Integer idCate, Integer idCate2);

    Page<Products> findAll(@Nullable Specification<Products> spec, Pageable page);

    List<Products> findAllByTypeOfItemAndCategory_ParentId(Integer type, Integer pantId);

    List<Products> findAllByTypeOfItemAndCategory_Id(Integer type, Integer idCate);

    List<Products> findAllByTypeOfItem(Integer id);

    List<Products> findAllByCategory_Id(Integer id);

    @Query(value = "{call filter_Sales_Products(:page_id)}", nativeQuery = true)
    List<Products> getListProductByCodeSale(@Param("page_id") Integer parentId);

    List<Products> findByNameContainingAndTypeOfItem(String name, int type);

    List<Products> findByNameContaining(String name);

}
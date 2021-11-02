package com.poly.repo;

import com.poly.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface ProductsRepository extends JpaRepository<Products, String>, JpaSpecificationExecutor<Products> {

    @Query(value="SELECT * FROM ( SELECT * , ROW_NUMBER() OVER (ORDER BY id) AS RowNum FROM products  where type_of_item =?1) AS MyDerivedTable WHERE MyDerivedTable.RowNum BETWEEN 1 AND 12",
            nativeQuery = true)
    List<Products> getListByCate(Integer idCate);

    @Query("select p from Products p where p.saleProduct.saleCode=:code")
    List<Products> getListByCodeSale(@Param("code") String code);
    List<Products> findAllByCategory_IdOrCategory_Id(Integer idCate, Integer idCate2);

    //Page<Products> findAllByCategory_IdOrCategory_Id(Integer idCate, Integer idCate2, Pageable page);

  //  List<Products> findAllByCategory_ParentId(Integer idCate);
    List<Products> findAllByTypeOfItemAndCategory_ParentId(Integer type,Integer pantId );
    List<Products> findAllByTypeOfItemAndCategory_Id(Integer type,Integer idCate );
    List<Products> findAllByTypeOfItem(Integer id);
    List<Products> findAllByCategory_Id(Integer id);
    //Page<Products> findAll(PageRequest of, Sort sort);

  //  Page<Products> findAllPage( Pageable page);
}
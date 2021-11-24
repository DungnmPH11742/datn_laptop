package com.poly.service;

import com.poly.entity.Products;
import com.poly.filter.ProductSearchCriteria;
import com.poly.vo.ProductsVO;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductsVO> getList();

    ProductsVO getOne(String id);

    List<ProductsVO> findByNameContainingAndTypeOfItem(String name, int type);

    ProductsVO create(ProductsVO vo);

    ProductsVO update(ProductsVO vo);

    void delete(String id);

    List<ProductsVO> getListPage(Optional<Integer> page, Optional<Integer> row);

    List<ProductsVO> getListByCate(Integer id);

    List<ProductsVO> getListByCodeSale(String code);

    Page<ProductsVO> getListByPageNumber(int page, int limit, List<ProductsVO> lstProductsVO, String sortPrice);

    List<ProductsVO> retrieveProducts(ProductSearchCriteria searchCriteria);

    Page<ProductsVO> findAllByNameLike(int page, int limit, String name);

    List<ProductsVO> getListProductByCodeSale(Integer parentId);


    List<ProductsVO> findAllByTypeOfItemAndCategory_ParentId(Integer type, Integer pantId);

    List<ProductsVO> findAllByTypeOfItemAndCategory_Id(Integer type, Integer idCate);

    List<ProductsVO> findAllByTypeOfItem(Integer id);

    List<ProductsVO> findAllByCategory_Id(Integer id);

    List<ProductsVO> findAllByCategory_IdOrCategory_Id(Integer idCate, Integer idCate2);

}

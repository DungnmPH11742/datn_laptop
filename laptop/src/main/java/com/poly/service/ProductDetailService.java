package com.poly.service;

import com.poly.filter.ProductSearchCriteria;
import com.poly.vo.ProductsDetailVO;
import com.poly.vo.response.ProductsReponseVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductDetailService {
    ProductsDetailVO getOne(String id);

    ProductsReponseVO findBySkuProduct(String sku);

    ProductsDetailVO findBySku(String sku);

    ProductsDetailVO save(ProductsDetailVO vo);

    boolean delete(String sku);

    List<ProductsDetailVO> findAllByTypeOfItemAndCategory_ParentId(String type, String parentId);

    List<ProductsDetailVO> findAllByTypeOfItemAndCategory_Id(String type, String idCate);

    List<ProductsDetailVO> findAllByTypeOfItem(String id);

    List<ProductsDetailVO> findAllByCategory_Id(String id);

    List<ProductsDetailVO> retrieveProducts(ProductSearchCriteria searchCriteria);

    Page<ProductsDetailVO> getListByPageNumber(int page, int limit, List<ProductsDetailVO> lstProductsVO, String sortPrice);

    List<ProductsDetailVO> findAllByNameLikeHome(String name);

    List<ProductsDetailVO> findAllByCategory_IdOrCategory_Id(String idCate, String idCate2);

    Page<ProductsDetailVO> findAllByNameLike(int page, int limit, String name, String type, String sort);

    List<ProductsDetailVO> retrieveProductsSearch(ProductSearchCriteria searchCriteria);
}

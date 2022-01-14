package com.poly.service;

import com.poly.vo.ProductsVO;
import com.poly.vo.request.ProductRequestVO;
import com.poly.vo.response.ProductsReponseVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductsVO> getList();

    List<ProductsReponseVO> findAllSku();

    List<ProductsReponseVO> findAllSkuActive();

    ProductsVO getOne(String id);

    ProductsVO getProductById(String id);

    ProductsReponseVO getProductBySku(String sku);

    List<ProductsVO> findByNameContainingAndTypeOfItem(String name, String type);

    List<ProductsReponseVO> findByNameContainingAndTypeOfItemAndSku(String name, String type);

    ProductsVO create(ProductRequestVO vo);

    ProductsVO update(ProductRequestVO vo);

    boolean delete(String id);

    List<ProductsVO> getListPage(Optional<Integer> page, Optional<Integer> row);

    List<ProductsVO> getListByCate(String id);

    List<ProductsVO> getListByCodeSale(String code);

    /*Page<ProductsVO> getListByPageNumber(int page, int limit, List<ProductsVO> lstProductsVO, String sortPrice);

    List<ProductsVO> retrieveProducts(ProductSearchCriteria searchCriteria);

    Page<ProductsVO> findAllByNameLike(int page, int limit, String name, String type, String sort);

    List<ProductsVO> getListProductByCodeSale(String parentId);


    List<ProductsVO> findAllByTypeOfItemAndCategory_ParentId(String type, String pantId);

    List<ProductsVO> findAllByTypeOfItemAndCategory_Id(String type, String idCate);

    List<ProductsVO> findAllByTypeOfItem(String id);

    List<ProductsVO> findAllByCategory_Id(String id);

    List<ProductsVO> findAllByCategory_IdOrCategory_Id(String idCate, String idCate2);

    List<ProductsVO> findAllByNameLikeHome(String name);*/
}

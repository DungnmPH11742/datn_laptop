package com.poly.service.impl;

import com.poly.entity.ProductsDetail;
import com.poly.filter.ProductSearchCriteria;
import com.poly.filter.ProductsSpecifications;
import com.poly.filter.ProductsSpecificationsSearch;
import com.poly.repo.ProductsDetailRepository;
import com.poly.repo.ProductsRepository;
import com.poly.service.ProductDetailService;
import com.poly.vo.ProductsDetailVO;
import com.poly.vo.response.ProductsReponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductsDetailRepository repository;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public ProductsReponseVO findBySkuProduct(String sku) {
        ProductsReponseVO reponseVO = new ProductsReponseVO();
        ProductsDetail detail = repository.findBySkuAndStatusNot(sku, -1);
        reponseVO = modelMapper.map(detail.getProduct(), ProductsReponseVO.class);
        reponseVO.setProductsDetail(modelMapper.map(detail, ProductsDetailVO.class));
        reponseVO.getProductsDetail().setIdProduct(reponseVO.getId());
        ProductsDetailVO vo = reponseVO.getProductsDetail();
        if (detail.getConnectivity() != null) {
            vo.setLstConnectivity(new ArrayList<>());
            Arrays.asList(detail.getConnectivity().split(", ")).forEach(val -> {
                vo.getLstConnectivity().add(val);
            });
        }
        return reponseVO;
    }

    @Override
    public ProductsDetailVO findBySku(String sku) {
        ProductsDetail detail = repository.findBySkuAndStatusNot(sku, -1);
        ProductsDetailVO vo = modelMapper.map(detail, ProductsDetailVO.class);
        vo.setIdProduct(detail.getProduct().getId());
        if (detail.getConnectivity() != null) {
            vo.setLstConnectivity(new ArrayList<>());
            Arrays.asList(detail.getConnectivity().split(", ")).forEach(val -> {
                vo.getLstConnectivity().add(val);
            });
        }
        return vo;
    }

    @Override
    public ProductsDetailVO save(ProductsDetailVO vo) {
        ProductsDetail detail = modelMapper.map(vo, ProductsDetail.class);
        detail.setProduct(productsRepository.findProductsById(vo.getIdProduct()));
        if (vo.getLstConnectivity() != null) {
            detail.setConnectivity(String.join(", ", vo.getLstConnectivity()));
        }
        detail.setProduct(productsRepository.findProductsById(vo.getIdProduct()));
        detail = repository.save(detail);
        vo = modelMapper.map(detail, ProductsDetailVO.class);
        vo.setIdProduct(detail.getProduct().getId());
        return vo;
    }

    @Override
    public boolean delete(String sku) {
        ProductsDetail detail = repository.findById(sku).get();
        detail.setStatus(-1);
        repository.save(detail);
        return true;
    }

    @Override
    public List<ProductsDetailVO> findAllByCategory_IdOrCategory_Id(String idCate, String idCate2) {
        return convertToListDto(repository.findAllByProduct_Category_IdOrProduct_Category_Id(idCate, idCate2));
    }

    @Override
    public List<ProductsDetailVO> findAllByTypeOfItemAndCategory_ParentId(String type, String parentId) {
        return convertToListDto(repository.findAllByProduct_TypeOfItemAndProduct_Category_ParentId(type, parentId));
    }

    @Override
    public List<ProductsDetailVO> findAllByTypeOfItemAndCategory_Id(String type, String idCate) {
        return convertToListDto(repository.findAllByProduct_TypeOfItemAndProduct_Category_Id(type, idCate));
    }

    @Override
    public List<ProductsDetailVO> findAllByTypeOfItem(String id) {
        return convertToListDto(repository.findAllByProduct_TypeOfItem(id));
    }

    @Override
    public List<ProductsDetailVO> findAllByCategory_Id(String id) {
        return convertToListDto(repository.findAllByProduct_Category_Id(id));
    }

    @Override
    public Page<ProductsDetailVO> findAllByNameLike(int page, int limit, String name, String type, String sort) {
        List<ProductsDetailVO> lstProfuctsVOFindByName = convertToListDto(repository.findAllByProduct_NameIgnoreCaseContainingAndProduct_TypeOfItemIgnoreCaseContaining(name, type));

        if (sort.equals("asc")) {
            Collections.sort(lstProfuctsVOFindByName, new Comparator<ProductsDetailVO>() {
                @Override
                public int compare(ProductsDetailVO o1, ProductsDetailVO o2) {
                    return o1.getPrice().compareTo(o2.getPrice());
                }
            });
        }
        if (sort.equals("desc")) {
            Collections.sort(lstProfuctsVOFindByName, new Comparator<ProductsDetailVO>() {
                @Override
                public int compare(ProductsDetailVO o1, ProductsDetailVO o2) {
                    return o2.getPrice().compareTo(o1.getPrice());
                }
            });
        }
        Pageable pageable = PageRequest.of(page - 1, limit);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), lstProfuctsVOFindByName.size());

        Page<ProductsDetailVO> pageProducts = new PageImpl<>(lstProfuctsVOFindByName.subList(start, end), pageable, lstProfuctsVOFindByName.size());

        return pageProducts;
    }


    @Override
    public List<ProductsDetailVO> retrieveProducts(ProductSearchCriteria searchCriteria) {
        List<ProductsDetailVO> productsVOList = new ArrayList<>();

        Specification<ProductsDetail> productsFilterSpecification = ProductsSpecifications.createProductSpecification(searchCriteria);

        productsVOList = convertToListDto(this.repository.findAll(productsFilterSpecification));

        return productsVOList;
    }

    @Override
    public Page<ProductsDetailVO> getListByPageNumber(int page, int limit, List<ProductsDetailVO> lstProductsVO, String sortPrice) {
        if (sortPrice.equals("asc")) {
            Collections.sort(lstProductsVO, new Comparator<ProductsDetailVO>() {
                @Override
                public int compare(ProductsDetailVO o1, ProductsDetailVO o2) {
                    return o1.getPrice().compareTo(o2.getPrice());
                }
            });
        }
        if (sortPrice.equals("desc")) {
            Collections.sort(lstProductsVO, new Comparator<ProductsDetailVO>() {
                @Override
                public int compare(ProductsDetailVO o1, ProductsDetailVO o2) {
                    return o2.getPrice().compareTo(o1.getPrice());
                }
            });
        }


        Pageable pageable = PageRequest.of(page - 1, limit);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), lstProductsVO.size());
        Page page1 = new PageImpl(lstProductsVO.subList(start, end), pageable, lstProductsVO.size());
        return page1;
    }

    @Override
    public List<ProductsDetailVO> findAllByNameLikeHome(String name) {
        return (name.isEmpty()) ? convertToListDto(repository.findAll()) : convertToListDto(repository.findAllByProduct_NameIgnoreCaseContainingAndStatusEquals(name, 1));
    }

    @Override
    public ProductsDetailVO getOne(String id) {
        ProductsDetail products = repository.getById(id);
        // ProductsDetailVO vo = modelMapper.map(products, ProductsDetailVO.class);
        return convertToDto(products);
    }

    private ProductsDetailVO convertToDto(ProductsDetail products) {
        ProductsDetailVO productsVO = modelMapper.map(products, ProductsDetailVO.class);
        productsVO.setNameProduct(productsVO.getProduct().getName());
        productsVO.setIdProduct(productsVO.getProduct().getId());
        productsVO.setTypeOfItem(productsVO.getProduct().getTypeOfItem());
        return productsVO;
    }

    private List<ProductsDetailVO> convertToListDto(List<ProductsDetail> lstProducts) {
        List<ProductsDetailVO> vos = new ArrayList<>();
        lstProducts.forEach(products -> {
            vos.add(modelMapper.map(products, ProductsDetailVO.class));
//            vos.forEach(f -> f.setIdProduct(products.getProduct().getId()));
//            vos.forEach(f -> f.setNameProduct(products.getProduct().getName()));
        });
        vos.forEach(r -> r.setNameProduct(r.getProduct().getName()));
        vos.forEach(r -> r.setIdProduct(r.getProduct().getId()));
        vos.forEach(r -> r.setTypeOfItem(r.getProduct().getTypeOfItem()));
        return vos;
    }

    @Override
    public List<ProductsDetailVO> retrieveProductsSearch(ProductSearchCriteria searchCriteria) {
        List<ProductsDetailVO> productsVOList = new ArrayList<>();

        Specification<ProductsDetail> productsFilterSpecification = ProductsSpecificationsSearch.createProductSpecification(searchCriteria);

        productsVOList = convertToListDto(this.repository.findAll(productsFilterSpecification));

        return productsVOList;
    }
}

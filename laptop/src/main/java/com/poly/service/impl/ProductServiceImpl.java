package com.poly.service.impl;

import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;
import com.poly.repo.ProductsDetailRepository;
import com.poly.repo.ProductsRepository;
import com.poly.service.ProductService;
import com.poly.vo.ProductsDetailVO;
import com.poly.vo.ProductsVO;
import com.poly.vo.request.ProductRequestVO;
import com.poly.vo.response.ProductsReponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsDetailRepository detailRepository;

    @Autowired
    private ModelMapper modelMapper;

    private LocalDate date;

    @Override
    public List<ProductsVO> getList() {
        List<ProductsVO> vos = new ArrayList<>();
        productsRepository.findAll().forEach(products -> {
            ProductsVO vo = modelMapper.map(products, ProductsVO.class);
            vo.getProductsDetails().forEach(val -> {
                val.setIdProduct(vo.getId());
                ProductsDetail detail = detailRepository.findById(val.getSku()).get();
                if (detail.getConnectivity() != null) {
                    val.setLstConnectivity(new ArrayList<>());
                    Arrays.asList(detail.getConnectivity().split(", ")).forEach(data -> {
                        val.getLstConnectivity().add(data);
                    });
                }
            });
            vos.add(vo);
        });
        return vos;
    }

    @Override
    public List<ProductsReponseVO> findAllSku() {
        List<ProductsReponseVO> reponseVOS = new ArrayList<>();
        productsRepository.findAllByActiveNot(-1).forEach(products -> {
            products.getProductsDetails().forEach(val -> {
                ProductsReponseVO vo = modelMapper.map(products, ProductsReponseVO.class);
                vo.setProductsDetail(modelMapper.map(val, ProductsDetailVO.class));
                vo.getProductsDetail().setIdProduct(products.getId());
                reponseVOS.add(vo);
            });
        });
        return reponseVOS;
    }

    @Override
    public List<ProductsVO> findAllSkuNotDel() {
        List<ProductsVO> reponseVOS = new ArrayList<>();
        productsRepository.findAll().forEach(products -> {
            ProductsVO vo = modelMapper.map(products, ProductsVO.class);
            vo.setProductsDetails(new ArrayList<>());
            products.getProductsDetails().forEach(val -> {
                if (val.getStatus() != -1) {
                    ProductsDetailVO detailVO = modelMapper.map(val, ProductsDetailVO.class);
                    detailVO.setIdProduct(vo.getId());
                    ProductsDetail detail = detailRepository.findById(val.getSku()).get();
                    if (detail.getConnectivity() != null) {
                        detailVO.setLstConnectivity(new ArrayList<>());
                        Arrays.asList(detail.getConnectivity().split(", ")).forEach(data -> {
                            detailVO.getLstConnectivity().add(data);
                        });
                    }
                    vo.getProductsDetails().add(detailVO);
                }
            });
            reponseVOS.add(vo);
        });
        return reponseVOS;
    }

    @Override
    public List<ProductsReponseVO> findAllSkuActive() {
        List<ProductsReponseVO> reponseVOS = new ArrayList<>();
        productsRepository.findByActive(1).forEach(products -> {
            products.getProductsDetails().forEach(val -> {
                if (val.getStatus() == 1) {
                    ProductsReponseVO vo = modelMapper.map(products, ProductsReponseVO.class);
                    vo.setProductsDetail(modelMapper.map(val, ProductsDetailVO.class));
                    vo.getProductsDetail().setIdProduct(products.getId());
                    if (val.getSaleProduct() != null) {
                        Date day = Date.valueOf(date.now());
                        if (val.getSaleProduct().getStatus() != 1 || val.getSaleProduct().getDateOff().compareTo(day) < 0) {
                            vo.getProductsDetail().setSaleProduct(null);
                        }
                    }
                    reponseVOS.add(vo);
                }
            });
        });
        return reponseVOS;
    }

    @Override
    public ProductsVO getOne(String id) {
        Products products = productsRepository.getById(id);
        ProductsVO vo = modelMapper.map(products, ProductsVO.class);
        vo.getProductsDetails().forEach(val -> {
            val.setIdProduct(vo.getId());
        });
        return vo;
    }

    @Override
    public ProductsVO getProductById(String id) {
        Products products = this.productsRepository.findProductsById(id);
        if (products != null) {
            ProductsVO vo = modelMapper.map(products, ProductsVO.class);
            vo.getProductsDetails().forEach(val -> {
                val.setIdProduct(vo.getId());
            });
            return vo;
        }
        return null;
    }

    @Override
    public ProductsReponseVO getProductBySku(String sku) {
        ProductsReponseVO reponseVO = modelMapper.map(productsRepository.findProductsBySku(sku), ProductsReponseVO.class);
        reponseVO.setProductsDetail(modelMapper.map(detailRepository.findBySkuAndStatusNot(sku, -1), ProductsDetailVO.class));
        return reponseVO;
    }

    @Override
    public List<ProductsVO> findByNameContainingAndTypeOfItem(String name, String type) {
        List<ProductsVO> vos = new ArrayList<>();
        (type != "" ? productsRepository.findByNameContainingAndTypeOfItem(name, type) : productsRepository.findByNameContaining(name)).forEach(products -> {
            ProductsVO vo = modelMapper.map(products, ProductsVO.class);
            vo.getProductsDetails().forEach(val -> {
                val.setIdProduct(vo.getId());
            });
            vos.add(modelMapper.map(products, ProductsVO.class));
        });
        return vos;
    }

    @Override
    public List<ProductsReponseVO> findByNameContainingAndTypeOfItemAndSku(String name, String type) {
        List<ProductsReponseVO> vos = new ArrayList<>();
        (type != "" ? productsRepository.findByNameContainingAndActiveAndTypeOfItem(name, 1, type) : productsRepository.findByNameContaining(name)).forEach(products -> {
            products.getProductsDetails().forEach(val -> {
                if (val.getStatus() == 1) {
                    ProductsReponseVO vo = modelMapper.map(products, ProductsReponseVO.class);
                    vo.setProductsDetail(modelMapper.map(val, ProductsDetailVO.class));
                    vo.getProductsDetail().setIdProduct(vo.getId());
                    vos.add(vo);
                }
            });
        });
        return vos;
    }

    @Override
    public ProductsVO create(ProductRequestVO vo) {
        Products entity = this.modelMapper.map(vo, Products.class);
//        entity.setProductsDetails(null);
//        ProductsDetail productsDetail = this.modelMapper.map(vo.getProductsDetails(), ProductsDetail.class);
//        Optional<Products> productsOptional = this.productsRepository.findById(vo.getId());
        System.err.println(vo);
//        Optional<ProductsDetail> optionalProductsDetail = this.productsDetailRepository.findById(vo.getId());
//         && !optionalProductsDetail.isPresent()
//        if (!productsOptional.isPresent()) {
//            this.productsRepository.save(entity);
//            productsDetail.setId(vo.getId());
//            this.productsDetailRepository.save(productsDetail);
        productsRepository.save(entity);
        return modelMapper.map(entity, ProductsVO.class);
//        } else {
//            return null;
//        }
    }

    @Override
    public ProductsVO update(ProductRequestVO vo) {
        Products products = modelMapper.map(vo, Products.class);
//        Optional<Products> optionalProducts = this.productsRepository.findById(vo.getId());
//        ProductsDetail productsDetail = modelMapper.map(vo.getProductsDetails(), ProductsDetail.class);

//        productsDetailRepository.save(productsDetail);
        return modelMapper.map(productsRepository.save(products), ProductsVO.class);
//        Optional<ProductsDetail> optionalProductsDetail = this.productsDetailRepository.findById(vo.getId());
//        if (optionalProducts.isPresent() && optionalProductsDetail.isPresent()) {
//            Products products = optionalProducts.get();
//            ProductsDetail productsDetail = optionalProductsDetail.get();
//            vo.getProductsDetail().setId(productsDetail.getId());
//            BeanUtils.copyProperties(vo, products);
//            BeanUtils.copyProperties(vo.getProductsDetail(), productsDetail);
//            productsRepository.save(products);
//            productsDetailRepository.save(productsDetail);
//            return vo;
//        } else {
//        return null;
//        }

    }

    @Override
    public boolean delete(String id) {
        Optional<Products> optionalProducts = this.productsRepository.findById(id);
        if (optionalProducts.isPresent()) {
            Products products = optionalProducts.get();
            if (products.getProductsDetails().size() == 1) {
                products.setActive(-1);
            }
            this.productsRepository.save(products);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<ProductsVO> getListPage(Optional<Integer> page, Optional<Integer> row) {
        return null;
    }

    @Override
    public List<ProductsVO> getListByCate(String id) {
        List<ProductsVO> vos = new ArrayList<>();
        productsRepository.getListByCate(id).forEach(products -> {
            vos.add(modelMapper.map(products, ProductsVO.class));
        });
        return vos;
    }

    @Override
    public List<ProductsVO> getListByCodeSale(String code) {
        List<ProductsVO> vos = new ArrayList<>();
        /*productsRepository.getListByCodeSale(code).forEach(products -> {
            vos.add(modelMapper.map(products, ProductsVO.class));
        });*/
        return vos;
    }
/*
    @Override
    public List<ProductsVO> getListProductByCodeSale(String parentId) {
        return convertToListDto(productsRepository.getListProductByCodeSale(parentId));
    }

    @Override
    public List<ProductsVO> findAllByTypeOfItemAndCategory_ParentId(String type, String parentId) {
        return convertToListDto(productsRepository.findAllByTypeOfItemAndCategory_ParentId(type, parentId));
    }

    @Override
    public List<ProductsVO> findAllByTypeOfItemAndCategory_Id(String type, String idCate) {
        return convertToListDto(productsRepository.findAllByTypeOfItemAndCategory_Id(type, idCate));
    }

    @Override
    public List<ProductsVO> findAllByTypeOfItem(String id) {
        return convertToListDto(productsRepository.findAllByTypeOfItem(id));
    }

    @Override
    public List<ProductsVO> findAllByCategory_Id(String id) {
        return convertToListDto(productsRepository.findAllByCategory_Id(id));
    }

    @Override
    public List<ProductsVO> findAllByCategory_IdOrCategory_Id(String idCate, String idCate2) {
        return convertToListDto(productsRepository.findAllByCategory_IdOrCategory_Id(idCate, idCate2));
    }

    private ProductsVO convertToDto(Products products) {
        ProductsVO productsVO = modelMapper.map(products, ProductsVO.class);
        return productsVO;
    }

    private List<ProductsVO> convertToListDto(List<Products> lstProducts) {
        List<ProductsVO> vos = new ArrayList<>();
        lstProducts.forEach(products -> {
            vos.add(modelMapper.map(products, ProductsVO.class));
        });
        return vos;
    }

    @Override
    public List<ProductsVO> findAllByNameLikeHome(String name) {
        return convertToListDto(productsRepository.findAllByNameLikeHome(name));
    }*/
}

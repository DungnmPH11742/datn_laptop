package com.poly.service.impl;

import com.poly.entity.ProductsDetail;
import com.poly.repo.ProductsDetailRepository;
import com.poly.repo.ProductsRepository;
import com.poly.service.ProductDetailService;
import com.poly.vo.ProductsDetailVO;
import com.poly.vo.response.ProductsReponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return reponseVO;
    }

    @Override
    public ProductsDetailVO findBySku(String sku) {
        ProductsDetail detail = repository.findBySkuAndStatusNot(sku, -1);
        ProductsDetailVO vo = modelMapper.map(detail, ProductsDetailVO.class);
        vo.setIdProduct(detail.getProduct().getId());
        return vo;
    }

    @Override
    public ProductsDetailVO save(ProductsDetailVO vo) {
        ProductsDetail detail = modelMapper.map(vo, ProductsDetail.class);
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
}

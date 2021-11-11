package com.poly.service.impl;

import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;
import com.poly.repo.ProductsDetailRepository;
import com.poly.service.ProductDetailService;
import com.poly.vo.ProductsDetailVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    private ProductsDetailRepository productsDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductsDetailVO> getList() {
        List<ProductsDetailVO> voList = new ArrayList<>();
        this.productsDetailRepository.findAll().forEach(p ->{
            voList.add(this.modelMapper.map(p,ProductsDetailVO.class));
        });
        return voList;
    }

    @Override
    public ProductsDetailVO getOne(String id) {
        Optional<ProductsDetail> optional = this.productsDetailRepository.findById(id);
        if (optional.isPresent()){
            return this.modelMapper.map(optional.get(),ProductsDetailVO.class);
        }else {
            return null;
        }

    }

    @Override
    public ProductsDetailVO create(ProductsDetailVO vo) {
        ProductsDetail entity = this.modelMapper.map(vo,ProductsDetail.class);
        Optional<ProductsDetail> productsOptional = this.productsDetailRepository.findById(vo.getId());
        if (!productsOptional.isPresent()){
            this.productsDetailRepository.save(entity);
            return vo;
        }else {
            return null;
        }
    }

    @Override
    public ProductsDetailVO update(ProductsDetailVO vo) {
        Optional<ProductsDetail> optionalProducts = this.productsDetailRepository.findById(vo.getId());
        if (optionalProducts.isPresent()){
            ProductsDetail productsDetail = optionalProducts.get();
            BeanUtils.copyProperties(vo,productsDetail);
            this.productsDetailRepository.save(productsDetail);
            return  vo;
        }else {
            return null;
        }
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}

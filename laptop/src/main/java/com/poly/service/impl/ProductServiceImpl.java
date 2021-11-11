package com.poly.service.impl;

import com.poly.entity.Products;
import com.poly.repo.ProductsRepository;
import com.poly.service.ProductService;
import com.poly.vo.ProductsVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductsVO> getList() {
        List<ProductsVO> vos = new ArrayList<>();
        productsRepository.findAll().forEach(products -> {
            vos.add(modelMapper.map(products, ProductsVO.class));
        });
        return vos;
    }

    @Override
    public ProductsVO getOne(String id) {
        Optional<Products> productsOptional = this.productsRepository.findById(id);
        if (productsOptional.isPresent()){
            return modelMapper.map(productsOptional.get(), ProductsVO.class);
        }else {
            return null;
        }

    }

    @Override
    public ProductsVO create(ProductsVO vo) {
        Products entity = this.modelMapper.map(vo,Products.class);
        Optional<Products> productsOptional = this.productsRepository.findById(vo.getId());
        if (!productsOptional.isPresent()){
            this.productsRepository.save(entity);
            return vo;
        }else {
            return null;
        }

    }

    @Override
    public ProductsVO update(ProductsVO vo) {
        Optional<Products> optionalProducts = this.productsRepository.findById(vo.getId());
        if (optionalProducts.isPresent()){
            Products products = optionalProducts.get();
            if (vo.getDateOn() == null){
                vo.setDateOn(products.getDateOn());
            }
            if (vo.getTypeOfItem() == null){
                vo.setTypeOfItem(products.getTypeOfItem());
            }
            if (vo.getUnit() == null){
                vo.setUnit(products.getUnit());
            }
            if (vo.getName() == null){
                vo.setName(products.getName());
            }
            BeanUtils.copyProperties(vo,products);
            this.productsRepository.save(products);
            return  vo;
        }else {
            return null;
        }

    }

    @Override
    public boolean delete(String id) {
        Optional<Products> optionalProducts = this.productsRepository.findById(id);
        if (optionalProducts.isPresent()){
            Products products = optionalProducts.get();
            products.setActive(false);
            this.productsRepository.save(products);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<ProductsVO> getListPage(Optional<Integer> page, Optional<Integer> row) {
        return null;
    }

    @Override
    public List<ProductsVO> getListByCate(Integer id) {
        List<ProductsVO> vos = new ArrayList<>();
        productsRepository.getListByCate(id).forEach(products -> {
            vos.add(modelMapper.map(products, ProductsVO.class));
        });
        return vos;
    }

    @Override
    public List<ProductsVO> getListByCodeSale(String code) {
        List<ProductsVO> vos = new ArrayList<>();
        productsRepository.getListByCodeSale(code).forEach(products -> {
            vos.add(modelMapper.map(products, ProductsVO.class));
        });
        return vos;
    }
}

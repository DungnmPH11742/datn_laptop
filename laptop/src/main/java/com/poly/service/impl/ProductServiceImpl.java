package com.poly.service.impl;

import com.poly.repo.ProductsRepository;
import com.poly.service.ProductService;
import com.poly.vo.ProductsVO;
import org.modelmapper.ModelMapper;
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
        return modelMapper.map(productsRepository.getById(id), ProductsVO.class);
    }

    @Override
    public ProductsVO create(ProductsVO vo) {
        return null;
    }

    @Override
    public ProductsVO update(ProductsVO vo) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<ProductsVO> getListPage(Optional<Integer> page, Optional<Integer> row) {
        return null;
    }

    @Override
    public List<ProductsVO> getListForCate(Integer id){
        List<ProductsVO> vos = new ArrayList<>();
        productsRepository.getListForCate(id).forEach(products -> {
            vos.add(modelMapper.map(products, ProductsVO.class));
        });
        return vos;
    }
}

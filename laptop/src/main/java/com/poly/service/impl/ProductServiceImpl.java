package com.poly.service.impl;

import com.poly.entity.Products;
import com.poly.repo.CategoryRepository;
import com.poly.repo.ProductsRepository;
import com.poly.service.CategoryService;
import com.poly.service.ProductService;
import com.poly.vo.ProductsVO;
import com.poly.vo.SaleProductVO;
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
    @Autowired private CategoryService categoryService;

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
        Products products = productsRepository.getById(id);
<<<<<<< HEAD
        ProductsVO vo = modelMapper.map(products, ProductsVO.class);
        vo.setCategory(this.categoryService.getOne(products.getCategory().getId()));
        return vo;
=======
        ProductsVO productsVO = modelMapper.map(products, ProductsVO.class);
        if(products.getSaleProduct() != null){
            SaleProductVO saleProductVO = modelMapper.map(products.getSaleProduct(),SaleProductVO.class);
            productsVO.setSaleProduct(saleProductVO);
        }
        return productsVO;
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
    }

    @Override
    public List<ProductsVO> findByNameContainingAndTypeOfItem(String name, int type) {
        List<ProductsVO> vos = new ArrayList<>();
        productsRepository.findByNameContainingAndTypeOfItem(name, type).forEach(products -> {
            vos.add(modelMapper.map(products, ProductsVO.class));
        });
        return vos;
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

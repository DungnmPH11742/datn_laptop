package com.poly.service.impl;

import com.poly.entity.Products;
<<<<<<< HEAD
import com.poly.entity.ProductsDetail;
import com.poly.repo.CategoryRepository;
import com.poly.repo.ProductsDetailRepository;
=======
import com.poly.repo.CategoryRepository;
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2
import com.poly.repo.ProductsRepository;
import com.poly.service.CategoryService;
import com.poly.service.ProductService;
import com.poly.vo.ProductsVO;
import com.poly.vo.SaleProductVO;
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
    @Autowired private CategoryService categoryService;
<<<<<<< HEAD
    @Autowired private ProductsDetailRepository productsDetailRepository;
=======
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2

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
<<<<<<< HEAD
        Optional<Products> productsOptional = this.productsRepository.findById(id);
        Optional<ProductsDetail> optionalProductsDetail = this.productsDetailRepository.findById(id);
        if (productsOptional.isPresent() && optionalProductsDetail.isPresent()){
            productsOptional.get().setProductsDetail(optionalProductsDetail.get());
            return modelMapper.map(productsOptional.get(), ProductsVO.class);
        }else {
            return null;
        }
=======
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
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2
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
        Products entity = this.modelMapper.map(vo,Products.class);
        entity.getProductsDetail().setId(vo.getId());
        ProductsDetail productsDetail = this.modelMapper.map(vo.getProductsDetail(),ProductsDetail.class);
        Optional<Products> productsOptional = this.productsRepository.findById(vo.getId());
        Optional<ProductsDetail> optionalProductsDetail = this.productsDetailRepository.findById(vo.getId());
        if (!productsOptional.isPresent() && !optionalProductsDetail.isPresent()){
            productsDetail.setId(vo.getId());
            this.productsRepository.save(entity);
            this.productsDetailRepository.save(productsDetail);
            vo.getProductsDetail().setId(vo.getId());
            return vo;
        }else {
            return null;
        }
    }

    @Override
    public ProductsVO update(ProductsVO vo) {
        Optional<Products> optionalProducts = this.productsRepository.findById(vo.getId());
        Optional<ProductsDetail> optionalProductsDetail = this.productsDetailRepository.findById(vo.getId());
        if (optionalProducts.isPresent() && optionalProductsDetail.isPresent()){
            Products products = optionalProducts.get();
            ProductsDetail productsDetail = optionalProductsDetail.get();
            vo.getProductsDetail().setId(productsDetail.getId());
            BeanUtils.copyProperties(vo,products);
            BeanUtils.copyProperties(vo.getProductsDetail(),productsDetail);
            this.productsRepository.save(products);
            this.productsDetailRepository.save(productsDetail);
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

package com.poly.service.impl;

import com.poly.entity.Category;
import com.poly.entity.Products;
import com.poly.filter.ProductSearchCriteria;
import com.poly.filter.ProductsSpecifications;
import com.poly.repo.CategoryRepository;
import com.poly.repo.ProductsRepository;
import com.poly.service.ProductService;
import com.poly.vo.ProductsVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private CategoryRepository categoryRepository;

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
    @Override
    public Page<Products> getListByPageNumber(int page,int limit, List<Products> lstProducts, String sortPrice) {
        if(sortPrice.equals("asc")){
            Collections.sort(lstProducts, new Comparator<Products>() {
                @Override
                public int compare(Products p1, Products p2) {
                    return p1.getOutputPrice().compareTo(p2.getOutputPrice());
                }


            });
        }
        if(sortPrice.equals("desc")){
            Collections.sort(lstProducts, new Comparator<Products>() {
                @Override
                public int compare(Products p1, Products p2) {
                    return p2.getOutputPrice().compareTo(p1.getOutputPrice());
                }


            });
        }


        Pageable pageable = PageRequest.of(page - 1, limit);
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), lstProducts.size());
        Page page1 = new PageImpl<>(lstProducts.subList(start,end), pageable,lstProducts.size() );
        return  page1;
    }


    @Override
    public List<Products> retrieveProducts(ProductSearchCriteria searchCriteria)  {
        Specification<Products> productsFilterSpecification = ProductsSpecifications.createProductSpecification(searchCriteria);
        return this.productsRepository.findAll(productsFilterSpecification);
    }
    @Override
    public Page<Products> findAllByNameLike(int page,int limit,String name,Integer cateParent)
    {
        System.err.println("name: "+name);
        List<Products> result = null;

        List<Products> lstProfucts = productsRepository.findAll();
        Optional<Category> cate =  categoryRepository.findById(cateParent);

        result = ( name.equals("")) ?   lstProfucts.stream().filter(x-> x.getTypeOfItem()==cate.get().getParentId())
                .collect(Collectors.toList()) :  productsRepository.findAllByNameLike(name);
        for(Products x:   lstProfucts = lstProfucts.stream().filter(x-> x.getTypeOfItem()==cate.get().getParentId())
                .collect(Collectors.toList())){
            System.err.println("teen sp laf: "+ x.getName());
        }
        Pageable pageable = PageRequest.of(page - 1, limit);
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), result.size());
        Page pageProducts = new PageImpl<>(result.subList(start,end), pageable,result.size() );

        return pageProducts;
    }

}

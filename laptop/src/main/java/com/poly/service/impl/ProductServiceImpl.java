package com.poly.service.impl;

import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;
import com.poly.filter.ProductSearchCriteria;
import com.poly.filter.ProductsSpecifications;
import com.poly.repo.ProductsDetailRepository;
import com.poly.repo.ProductsRepository;
import com.poly.service.ProductService;
import com.poly.vo.CategoryVO;
import com.poly.vo.ProductsVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductsDetailRepository productsDetailRepository;

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
        return modelMapper.map(products, ProductsVO.class);
    }

    @Override
    public List<ProductsVO> findByNameContainingAndTypeOfItem(String name, int type) {
        List<ProductsVO> vos = new ArrayList<>();
        (type != 0 ? productsRepository.findByNameContainingAndTypeOfItem(name, type) : productsRepository.findByNameContaining(name)).forEach(products -> {
            vos.add(modelMapper.map(products, ProductsVO.class));
        });
        return vos;
    }

    @Override
    public ProductsVO create(ProductsVO vo) {
        Products entity = this.modelMapper.map(vo, Products.class);
        entity.setProductsDetail(null);
        ProductsDetail productsDetail = this.modelMapper.map(vo.getProductsDetail(), ProductsDetail.class);
        Optional<Products> productsOptional = this.productsRepository.findById(vo.getId());
        Optional<ProductsDetail> optionalProductsDetail = this.productsDetailRepository.findById(vo.getId());
        if (!productsOptional.isPresent() && !optionalProductsDetail.isPresent()) {
            this.productsRepository.save(entity);
            productsDetail.setId(vo.getId());
            this.productsDetailRepository.save(productsDetail);
            vo.getProductsDetail().setId(vo.getId());
            return vo;
        } else {
            return null;
        }
    }

    @Override
    public ProductsVO update(ProductsVO vo) {
        Optional<Products> optionalProducts = this.productsRepository.findById(vo.getId());
        Optional<ProductsDetail> optionalProductsDetail = this.productsDetailRepository.findById(vo.getId());
        if (optionalProducts.isPresent() && optionalProductsDetail.isPresent()) {
            Products products = optionalProducts.get();
            ProductsDetail productsDetail = optionalProductsDetail.get();
            vo.getProductsDetail().setId(productsDetail.getId());
            BeanUtils.copyProperties(vo, products);
            BeanUtils.copyProperties(vo.getProductsDetail(), productsDetail);
            this.productsRepository.save(products);
            this.productsDetailRepository.save(productsDetail);
            return vo;
        } else {
            return null;
        }

    }

    @Override
    public boolean delete(String id) {
        Optional<Products> optionalProducts = this.productsRepository.findById(id);
        if (optionalProducts.isPresent()) {

            Products products = optionalProducts.get();
            products.setActive(false);
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
    public Page<ProductsVO> getListByPageNumber(int page, int limit, List<ProductsVO> lstProductsVO, String sortPrice) {
        if (sortPrice.equals("asc")) {
            Collections.sort(lstProductsVO, new Comparator<ProductsVO>() {
                @Override
                public int compare(ProductsVO p1, ProductsVO p2) {
                    return p1.getOutputPrice().compareTo(p2.getOutputPrice());
                }

            });
        }
        if (sortPrice.equals("desc")) {
            Collections.sort(lstProductsVO, new Comparator<ProductsVO>() {
                @Override
                public int compare(ProductsVO p1, ProductsVO p2) {
                    return p2.getOutputPrice().compareTo(p1.getOutputPrice());
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
    public List<ProductsVO> retrieveProducts(ProductSearchCriteria searchCriteria) {
        List<ProductsVO> productsVOList = new ArrayList<>();

        Specification<Products> productsFilterSpecification = ProductsSpecifications.createProductSpecification(searchCriteria);

        this.productsRepository.findAll(productsFilterSpecification).forEach(products -> {
            productsVOList.add(modelMapper.map(products, ProductsVO.class));
        });

        return productsVOList;
    }

    @Override
    public Page<ProductsVO> findAllByNameLike(int page, int limit, String name) {
        System.err.println("name: " + name);
        List<ProductsVO> result = null;

        List<ProductsVO> lstProfuctsVO = convertToListDto(productsRepository.findAll());
        List<ProductsVO> lstProfuctsVOFindByName = convertToListDto(productsRepository.findAllByNameLike(name));
        CategoryVO categoryVO = new CategoryVO();
        // Optional<Category> optional = categoryRepository.findById(cateParent);
        //if (optional.isPresent()) {
        //    Category category = optional.get();
        //    BeanUtils.copyProperties(categoryVO, category);
        // }
        result = (name.equals("")) ? lstProfuctsVO.stream().filter(x -> x.getTypeOfItem() == categoryVO.getParentId())
                .collect(Collectors.toList()) : lstProfuctsVOFindByName;
        Pageable pageable = PageRequest.of(page - 1, limit);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), result.size());
        Page pageProducts = new PageImpl<>(lstProfuctsVOFindByName.subList(start, end), pageable, result.size());

        return pageProducts;
    }

    @Override
    public List<ProductsVO> getListProductByCodeSale(Integer parentId) {
        return convertToListDto(productsRepository.getListProductByCodeSale(parentId));
    }

    @Override
    public List<ProductsVO> findAllByTypeOfItemAndCategory_ParentId(Integer type, Integer parentId) {
        return convertToListDto(productsRepository.findAllByTypeOfItemAndCategory_ParentId(type, parentId));
    }

    @Override
    public List<ProductsVO> findAllByTypeOfItemAndCategory_Id(Integer type, Integer idCate) {
        return convertToListDto(productsRepository.findAllByTypeOfItemAndCategory_Id(type, idCate));
    }

    @Override
    public List<ProductsVO> findAllByTypeOfItem(Integer id) {
        return convertToListDto(productsRepository.findAllByTypeOfItem(id));
    }

    @Override
    public List<ProductsVO> findAllByCategory_Id(Integer id) {
        return convertToListDto(productsRepository.findAllByCategory_Id(id));
    }

    @Override
    public List<ProductsVO> findAllByCategory_IdOrCategory_Id(Integer idCate, Integer idCate2) {
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
}

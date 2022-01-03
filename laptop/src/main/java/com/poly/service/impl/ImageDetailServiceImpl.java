package com.poly.service.impl;

import com.poly.entity.ImageDetail;
import com.poly.entity.Products;
import com.poly.repo.ImageDetailRepository;
import com.poly.repo.ProductsRepository;
import com.poly.service.ImageDetailService;
import com.poly.vo.ImageDetailVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageDetailServiceImpl implements ImageDetailService {
    @Autowired
    private ImageDetailRepository imageDetailRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ImageDetailVO> findByProductId(String id) {
        List<ImageDetailVO> vos = new ArrayList<>();
        imageDetailRepository.findByProductsDetailSku(id).forEach(imageDetail -> {
            vos.add(modelMapper.map(imageDetail, ImageDetailVO.class));
        });
        return vos;
    }

    @Override
    public ImageDetailVO save(ImageDetailVO vo) {
        /*ImageDetail imageDetail = modelMapper.map(vo, ImageDetail.class);
        Products products = productsRepository.getById(vo.getIdProduct());
        if (products == null) {
            return null;
        } else {
            imageDetail.setProduct(products);
            return modelMapper.map(imageDetailRepository.save(imageDetail), ImageDetailVO.class);
        }*/return null;
    }

    @Override
    public ImageDetailVO update(ImageDetailVO vo) {
       /* ImageDetail imageDetail = modelMapper.map(vo, ImageDetail.class);
        Products products = productsRepository.getById(vo.getIdProduct());
        if (products == null) {
            return null;
        } else {
            imageDetail.setProduct(products);
            return modelMapper.map(imageDetailRepository.save(imageDetail), ImageDetailVO.class);
        }*/return null;
    }

    @Override
    public boolean remove(ImageDetailVO vo) {
        return false;
    }
}

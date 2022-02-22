package com.poly.service.impl;

import com.poly.entity.ImageDetail;
import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;
import com.poly.repo.ImageDetailRepository;
import com.poly.repo.ProductsDetailRepository;
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
    private ProductsDetailRepository detailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ImageDetailVO> findByProductSku(String sku) {
        List<ImageDetailVO> vos = new ArrayList<>();
        imageDetailRepository.findByProductsDetailSku(sku).forEach(imageDetail -> {
            vos.add(modelMapper.map(imageDetail, ImageDetailVO.class));
        });
        return vos;
    }

    @Override
    public ImageDetailVO save(ImageDetailVO vo) {
        ImageDetail imageDetail = modelMapper.map(vo, ImageDetail.class);
        ProductsDetail productsDetail = detailRepository.findById(vo.getSku()).get();
        if (productsDetail == null) {
            return null;
        } else {
            imageDetail.setProductsDetail(productsDetail);
            return modelMapper.map(imageDetailRepository.save(imageDetail), ImageDetailVO.class);
        }
    }

    @Override
    public ImageDetailVO update(ImageDetailVO vo) {
        ImageDetail imageDetail = modelMapper.map(vo, ImageDetail.class);
        ProductsDetail productsDetail = detailRepository.findById(vo.getSku()).get();
        if (productsDetail == null) {
            return null;
        } else {
            imageDetail.setProductsDetail(productsDetail);
            return modelMapper.map(imageDetailRepository.save(imageDetail), ImageDetailVO.class);
        }
    }

    @Override
    public boolean remove(ImageDetailVO vo) {
        return false;
    }
}

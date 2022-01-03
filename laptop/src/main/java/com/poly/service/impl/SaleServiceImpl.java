package com.poly.service.impl;

import com.poly.entity.SaleProduct;
import com.poly.repo.SaleProductRepository;
import com.poly.service.SaleService;
import com.poly.vo.SaleProductVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    @Autowired
    private SaleProductRepository saleProductRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SaleProductVO> findAll() {
        List<SaleProductVO> vos = new ArrayList<>();
        saleProductRepository.findAll().forEach(saleProduct -> {
            vos.add(modelMapper.map(saleProduct, SaleProductVO.class));
        });
        return vos;
    }


    @Override
    public SaleProductVO save(SaleProductVO saleProductVO) {
        SaleProduct saleProduct = modelMapper.map(saleProductVO, SaleProduct.class);
        saleProduct.setSaleCode(RandomStringUtils.randomAlphanumeric(10));
        saleProduct  = saleProductRepository.save(saleProduct);
        modelMapper.map(saleProduct,saleProductVO);
        return saleProductVO;
    }

    @Override
    public SaleProductVO update(SaleProductVO saleProductVO) {
        SaleProduct saleProduct = modelMapper.map(saleProductVO,SaleProduct.class);
        saleProduct = saleProductRepository.save(saleProduct);
        modelMapper.map(saleProduct,saleProductVO);
        return saleProductVO;
    }

    @Override
    public boolean updateStatusSale(String saleCode) {
        if(saleProductRepository.getById(saleCode)!= null){
            saleProductRepository.updateStatusSale(saleCode);
            return true;
        }
        return false;

    }
}

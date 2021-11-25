package com.poly.service.impl;

import com.poly.entity.SaleProduct;
import com.poly.repo.SaleProductRepository;
import com.poly.service.SaleProductService;
import com.poly.vo.SaleProductVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleProductServiceImpl implements SaleProductService {
    @Autowired
    private SaleProductRepository  saleProductRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public SaleProductVO getSaleProduct(String saleCode) {
        SaleProduct saleProduct = this.saleProductRepository.getById(saleCode);
        if (saleProduct != null){
            return modelMapper.map(saleProduct,SaleProductVO.class);
        }
        return null;
    }
}

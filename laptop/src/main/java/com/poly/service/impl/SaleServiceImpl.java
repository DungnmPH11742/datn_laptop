package com.poly.service.impl;

import com.poly.repo.SaleProductRepository;
import com.poly.service.SaleService;
import com.poly.vo.SaleProductVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public SaleProductVO update(SaleProductVO saleProductVO) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}

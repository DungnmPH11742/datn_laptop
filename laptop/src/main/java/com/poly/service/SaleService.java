package com.poly.service;

import com.poly.vo.SaleProductVO;

import java.util.List;

public interface SaleService {
    List<SaleProductVO> findAll();

    SaleProductVO save(SaleProductVO saleProductVO);

    SaleProductVO update(SaleProductVO saleProductVO);

    boolean updateStatusSale(String saleCode);
}

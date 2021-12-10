package com.poly.service;

import com.poly.DTO.VouchersDTO;
import com.poly.entity.SaleProduct;

import java.util.List;

public interface VouchersService {
    List<VouchersDTO> findAll();

    VouchersDTO save(VouchersDTO dto);

    Boolean delete(String id);
}

package com.poly.service;

import com.poly.DTO.CategorySelectOption;
import com.poly.DTO.ProductSelectOption;
import com.poly.DTO.VouchersDTO;
import com.poly.vo.VouchersVO;

import java.util.List;

public interface VouchersService {

     VouchersVO getVoucherById(String id);

     VouchersVO getVoucherTrue(String id, String idProduct);

     List<VouchersDTO> findAll();

     VouchersDTO findById(String id);

     VouchersDTO create(VouchersDTO dto);

     VouchersDTO update(VouchersDTO dto);

     Boolean delete(String id);

     List<CategorySelectOption> cateSelectOption();

     List<ProductSelectOption> productsSelectOption();

}

package com.poly.service;

import com.poly.vo.VouchersVO;

public interface VouchersService {

     VouchersVO getVoucherById(String id);

     VouchersVO getVoucherTrue(String id, String idProduct);


}

package com.poly.service;

import com.poly.vo.OrderDetailsVO;

public interface OrderDetailService {

    OrderDetailsVO saveOderDetail(OrderDetailsVO orderDetailsVO);

    void updateQuantityOrderDetail(Integer quan, Integer id);
}

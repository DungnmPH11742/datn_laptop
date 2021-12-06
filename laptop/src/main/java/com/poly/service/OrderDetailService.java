package com.poly.service;

import com.poly.vo.OrderDetailsVO;

import java.util.List;

public interface OrderDetailService {

    void updateQuantityOrderDetail(Integer quan, Integer id);
    OrderDetailsVO saveOderDetail(OrderDetailsVO orderDetailsVO);

    OrderDetailsVO updateOrderDetail(OrderDetailsVO orderDetailsVO);
    List<OrderDetailsVO> getOrderDetailByIdOrder(Integer idOrder);
}

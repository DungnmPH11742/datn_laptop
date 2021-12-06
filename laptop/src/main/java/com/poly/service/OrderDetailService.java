package com.poly.service;

import com.poly.vo.OrderDetailsVO;

import java.util.List;

public interface OrderDetailService {

    void updateQuantityOrderDetail(Integer quan, Integer id);
    OrderDetailsVO saveOderDetail(OrderDetailsVO orderDetailsVO);

<<<<<<< HEAD
    OrderDetailsVO updateOrderDetail(OrderDetailsVO orderDetailsVO);
    List<OrderDetailsVO> getOrderDetailByIdOrder(Integer idOrder);
=======
    void updateQuantityOrderDetail(Integer quan, Integer id);
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
}

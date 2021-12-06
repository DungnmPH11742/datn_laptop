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
<<<<<<< HEAD
    OrderDetailsVO updateOrderDetail(OrderDetailsVO orderDetailsVO);
    List<OrderDetailsVO> getOrderDetailByIdOrder(Integer idOrder);
=======
    void updateQuantityOrderDetail(Integer quan, Integer id);
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2
}

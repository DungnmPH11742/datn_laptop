package com.poly.service;

import com.poly.DTO.CartItemDTO;
import com.poly.vo.OrderDetailsVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderDetailService {

    void updateQuantityOrderDetail(Integer quan, Integer id);

    OrderDetailsVO findById(int id);

    OrderDetailsVO saveOderDetail(OrderDetailsVO orderDetailsVO);

    OrderDetailsVO updateOrderDetail(OrderDetailsVO orderDetailsVO);

    List<OrderDetailsVO> getOrderDetailByIdOrder(Integer idOrder);

    Map<String, Object> deleteOrderDetail(String sku);
}

package com.poly.service;

import com.poly.entity.Account;
import com.poly.entity.Category;
import com.poly.entity.Orders;
import com.poly.vo.CategoryVO;
import com.poly.vo.OrdersVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    List<OrdersVO> getAllOrders();

    OrdersVO findidOrder(Integer id);

    OrdersVO saveOrders(OrdersVO orders);

    OrdersVO getByOrderCode(String code);
    OrdersVO updateOrders(OrdersVO orders);
     void deleteById(Integer id);
     List<OrdersVO> findOrdersByAccount(Integer id);

     OrdersVO findOrdersByAccountCart(String email);
    List<OrdersVO> findOrdersByAccountAndReceived(Integer id,Integer received);
}

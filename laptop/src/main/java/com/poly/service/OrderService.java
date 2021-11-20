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

    Orders saveOrders(Orders orders);

    Orders updateOrders(Orders orders);
     void deleteById(Integer id);
     Optional<Orders> findOrdersByAccount(Account account);;
}

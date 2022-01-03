package com.poly.service;

import com.poly.entity.Account;
import com.poly.entity.Category;
import com.poly.entity.Orders;
import com.poly.vo.CategoryVO;
import com.poly.vo.OrdersVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface OrderService {
    List<OrdersVO> getAllOrders();

    OrdersVO findIdOrder(Integer id);

    OrdersVO saveOrders(OrdersVO orders);

    OrdersVO getByOrderCode(String code);

    OrdersVO updateOrders(OrdersVO orders);

    void deleteById(Integer id);

    List<OrdersVO> findOrdersByAccount(Integer id);

    OrdersVO findOrderByIdAndAccount(Integer id, Integer idAccount);

    OrdersVO findOrdersByAccountCart(String email);

    List<OrdersVO> findOrdersByAccountAndReceived(Integer id,Integer received);

    Map<String,Object> getAllOrderPaging(Integer idAccount, int page, int size);

    Map<String,Object> getOrderByReceivedPaging(Integer idAccount,Integer received,int page,int size);

    List<OrdersVO> findAllOrdersByReceived(Integer received);
}

package com.poly.service.impl;

import com.poly.entity.Account;
import com.poly.entity.Category;
import com.poly.entity.Orders;
import com.poly.repo.CategoryRepository;
import com.poly.repo.OrdersRepository;
import com.poly.service.CategoryService;
import com.poly.service.OrderService;
import com.poly.vo.CategoryVO;
import com.poly.vo.OrdersVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<OrdersVO> getAllOrders() {
        List<OrdersVO> vos = new ArrayList<>();
        repository.findByReceivedIsNot(-2).forEach(orders -> {
            vos.add(modelMapper.map(orders, OrdersVO.class));
        });
        return vos;
    }

    @Override
    public OrdersVO findIdOrder(Integer id) {
        return modelMapper.map(repository.getById(1), OrdersVO.class);
    }

    @Override
    public OrdersVO saveOrders(OrdersVO orders) {
        return null;
    }

    @Override
    public OrdersVO getByOrderCode(String code) {
        return null;
    }


    @Override
    public OrdersVO updateOrders(OrdersVO vo) {
        return modelMapper.map(repository.save(modelMapper.map(vo, Orders.class)), OrdersVO.class);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<OrdersVO> findOrdersByAccount(Integer id) {
        return null;
    }

    @Override
    public OrdersVO findOrdersByAccountCart(String email) {
        return null;
    }

    @Override
    public List<OrdersVO> findOrdersByAccountAndReceived(Integer id, Integer received) {
        return null;
    }

}

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
        repository.findAll().forEach(orders -> {
            vos.add(modelMapper.map(orders, OrdersVO.class));
        });
        return vos;
    }

    @Override
    public Orders saveOrders(Orders orders) {
        return repository.save(orders);
    }

    @Override
    public Orders updateOrders(Orders orders) {
        return repository.save(orders);
    }

    @Override
    public void deleteById(Integer id) {
      repository.deleteById(id);
    }

    @Override
    public Optional<Orders> findOrdersByAccount(Account account) {
        return Optional.empty();
    }

}

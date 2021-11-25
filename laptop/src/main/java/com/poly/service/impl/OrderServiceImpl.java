package com.poly.service.impl;

import com.poly.entity.Account;
import com.poly.entity.Category;
import com.poly.entity.Orders;
import com.poly.repo.CategoryRepository;
import com.poly.repo.OrdersRepository;
import com.poly.service.CategoryService;
import com.poly.service.OrderService;
import com.poly.vo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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
    public OrdersVO findidOrder(Integer id) {
        Optional<Orders> orders = repository.findById(id);
        if(!orders.isPresent()){
            return null;
        }
        OrdersVO ordersVO = modelMapper.map(orders.get(),OrdersVO.class);
        AccountVO accountVO = modelMapper.map(orders.get().getAccount(),AccountVO.class);
        OrderDetailsVO orderDetailsVO = modelMapper.map(orders.get().getOrderDetails(),OrderDetailsVO.class);
        ProductsVO productsVO = modelMapper.map(orders.get().getOrderDetails().getProduct(),ProductsVO.class);
        if(orders.get().getOrderDetails().getVoucher()!= null){
            VouchersVO vouchersVO = modelMapper.map(orders.get().getOrderDetails().getVoucher(),VouchersVO.class);
        }

        orderDetailsVO.setProductsVO(productsVO);
        ordersVO.setAccountVO(accountVO);
        ordersVO.setDetailsVO(orderDetailsVO);
        return ordersVO;
    }

    @Override
    public OrdersVO saveOrders(OrdersVO vo) {
        Orders orders = modelMapper.map(vo,Orders.class);
        repository.save(orders);
        vo.setId(orders.getId());
        return vo;
    }

    @Override
    public OrdersVO getByOrderCode(String code) {
        Orders orders = this.repository.getOrderByCode(code);
        if (orders != null){
            OrdersVO ordersVO = new OrdersVO();
            modelMapper.map(orders,ordersVO);
            return ordersVO;
        }
        return null;
    }

    @Override
    public OrdersVO updateOrders(OrdersVO vo) {
        Orders orders = modelMapper.map(vo,Orders.class);
        repository.save(orders);
        return vo;
    }

    @Override
    public void deleteById(Integer id) {
      repository.deleteById(id);
    }

    @Override
    public List<OrdersVO> findOrdersByAccount(Integer idAccount) {
        List<OrdersVO> listVO = new ArrayList<>();
        if(!repository.findByIdAccount(idAccount).isEmpty()){
            repository.findByIdAccount(idAccount).forEach(orders -> {
                OrdersVO ordersVO = modelMapper.map(orders,OrdersVO.class);
                AccountVO accountVO = modelMapper.map(orders.getAccount(),AccountVO.class);
                OrderDetailsVO orderDetailsVO = modelMapper.map(orders.getOrderDetails(),OrderDetailsVO.class);
                ProductsVO productsVO = modelMapper.map(orders.getOrderDetails().getProduct(),ProductsVO.class);
                orderDetailsVO.setProductsVO(productsVO);
                ordersVO.setAccountVO(accountVO);
                ordersVO.setDetailsVO(orderDetailsVO);
                listVO.add(ordersVO);
            });
        }
        return listVO;
    }

}

package com.poly.service.impl;

import com.poly.entity.OrderDetails;
import com.poly.entity.Orders;
import com.poly.entity.Products;
import com.poly.repo.OrderDetailsRepository;
import com.poly.service.OrderDetailService;
import com.poly.vo.OrderDetailsVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailsRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDetailsVO saveOderDetail(OrderDetailsVO orderDetailsVO) {
        OrderDetails orderDetails = modelMapper.map(orderDetailsVO,OrderDetails.class);
        Products products = modelMapper.map(orderDetailsVO.getProductsVO(),Products.class);
        orderDetails.setProduct(products);
        Orders orders = modelMapper.map(orderDetailsVO.getOrdersVO(),Orders.class);
        orderDetails.setOrder(orders);
        this.repository.save(orderDetails);
        orderDetailsVO.setId(orderDetails.getId());
        return orderDetailsVO;
    }

    @Override
    public void updateQuantityOrderDetail(Integer quan, Integer id) {
        repository.updateQuantityOrderDetail(quan,id);
    }
}

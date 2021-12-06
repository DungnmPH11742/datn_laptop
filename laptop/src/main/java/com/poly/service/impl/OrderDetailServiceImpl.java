package com.poly.service.impl;

import com.poly.DTO.CartItemDTO;
import com.poly.entity.OrderDetails;
import com.poly.entity.Orders;
import com.poly.entity.Products;
import com.poly.repo.OrderDetailsRepository;
import com.poly.repo.OrdersRepository;
import com.poly.service.OrderDetailService;
import com.poly.vo.OrderDetailsVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailsRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public OrderDetailsVO saveOderDetail(OrderDetailsVO orderDetailsVO) {
        OrderDetails orderDetails = modelMapper.map(orderDetailsVO,OrderDetails.class);
        Products products = modelMapper.map(orderDetailsVO.getProducts(),Products.class);
        orderDetails.setProduct(products);
        Optional<Orders> orders = this.ordersRepository.findById(orderDetailsVO.getIdOrder());
        if (orders.isPresent()){
            orderDetails.setOrder(orders.get());
        }


        this.repository.save(orderDetails);
        orderDetailsVO.setId(orderDetails.getId());
        return orderDetailsVO;
    }

    @Override
    public OrderDetailsVO updateOrderDetail(OrderDetailsVO orderDetailsVO) {
        Optional<OrderDetails> orderDetails = this.repository.findById(orderDetailsVO.getId());
        if (orderDetails.isPresent()){
            modelMapper.map(orderDetailsVO,orderDetails.get());
            this.repository.save(orderDetails.get());
            return orderDetailsVO;
        }
        return null;
    }

    @Override
    public List<OrderDetailsVO> getOrderDetailByIdOrder(Integer idOrder) {
        List<OrderDetails> orderDetails = this.repository.getOrderDetailsByOrder(idOrder);
        if (orderDetails.size() > 0 || orderDetails.isEmpty()){
            List<OrderDetailsVO> listVo = new ArrayList<>();
            orderDetails.forEach(o -> {
                listVo.add(this.modelMapper.map(o,OrderDetailsVO.class));
            });
            return listVo;
        }
        return null;
    }
    @Override
    public void updateQuantityOrderDetail(Integer quan, Integer id) {
        repository.updateQuantityOrderDetail(quan,id);
    }

}

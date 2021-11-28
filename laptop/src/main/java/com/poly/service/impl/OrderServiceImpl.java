package com.poly.service.impl;

import com.poly.entity.Account;
import com.poly.entity.Category;
import com.poly.entity.Orders;
import com.poly.repo.CategoryRepository;
import com.poly.repo.OrdersRepository;
import com.poly.service.AccountService;
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
        List<OrderDetailsVO> detailsVOList = new ArrayList<>();
        orders.get().getOrderDetails().forEach(orderDetails -> {

            OrderDetailsVO orderDetailsVO = modelMapper.map(orderDetails,OrderDetailsVO.class);

            ProductsVO productsVO = modelMapper.map(orderDetails.getProduct(),ProductsVO.class);
            if(orderDetails.getVoucher()!=null){
                VouchersVO vouchersVO = modelMapper.map(orderDetails.getVoucher(),VouchersVO.class);
            }
            orderDetailsVO.setProductsVO(productsVO);
            detailsVOList.add(orderDetailsVO);

        });
        ordersVO.setOrderDetailsVO(detailsVOList);


        ordersVO.setAccountVO(accountVO);
        return ordersVO;
    }

    @Override
    public OrdersVO saveOrders(OrdersVO vo) {
        Orders orders = modelMapper.map(vo,Orders.class);
        Account account = modelMapper.map(vo.getAccountVO(),Account.class);
        orders.setAccount(account);
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
    public OrdersVO findOrdersByAccount(String email) {
        OrdersVO vo = new OrdersVO();
        if(repository.findByEmailAccount(email) != null){
           Orders  orders = repository.findByEmailAccount(email);
           vo = modelMapper.map(orders,OrdersVO.class);
            AccountVO accountVO = modelMapper.map(orders.getAccount(),AccountVO.class);
            List<OrderDetailsVO> detailsVOList = new ArrayList<>();
            orders.getOrderDetails().forEach(orderDetails -> {

                OrderDetailsVO orderDetailsVO = modelMapper.map(orderDetails,OrderDetailsVO.class);
                detailsVOList.add(orderDetailsVO);
                ProductsVO productsVO = modelMapper.map(orderDetails.getProduct(),ProductsVO.class);
                if(orderDetails.getProduct().getSaleProduct() != null){
                    SaleProductVO saleProductVO = modelMapper.map(orderDetails.getProduct().getSaleProduct(),SaleProductVO.class);
                    productsVO.setSaleProduct(saleProductVO);
                }
                if(orderDetails.getVoucher()!= null){

                    VouchersVO vouchersVO = modelMapper.map(orderDetails.getVoucher(),VouchersVO.class);
                    orderDetailsVO.setVouchersVO(vouchersVO);
                }
                orderDetailsVO.setProductsVO(productsVO);

            });

            vo.setAccountVO(accountVO);
            vo.setOrderDetailsVO(detailsVOList);
        }

        return vo;
    }

}

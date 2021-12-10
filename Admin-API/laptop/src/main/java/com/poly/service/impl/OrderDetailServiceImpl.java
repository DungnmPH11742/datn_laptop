 package com.poly.service.impl;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CartItemDTO;
import com.poly.entity.OrderDetails;
import com.poly.entity.Orders;
import com.poly.entity.Products;
import com.poly.repo.OrderDetailsRepository;
import com.poly.repo.OrdersRepository;
import com.poly.service.CartService;
import com.poly.service.OrderDetailService;
import com.poly.vo.OrderDetailsVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private HttpSession session;
    @Autowired
    private OrderDetailsRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartService cartService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public OrderDetailsVO saveOderDetail(OrderDetailsVO orderDetailsVO) {
        OrderDetails orderDetails = modelMapper.map(orderDetailsVO, OrderDetails.class);
        Products products = modelMapper.map(orderDetailsVO.getProduct(), Products.class);
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
            return modelMapper.map(repository.save(orderDetails.get()), OrderDetailsVO.class);
        }
        return null;
    }

    @Override
    public List<OrderDetailsVO> getOrderDetailByIdOrder(Integer idOrder) {
        List<OrderDetails> orderDetails = this.repository.getOrderDetailsByOrder(idOrder);
        if (orderDetails.size() > 0 || orderDetails.isEmpty()){
            List<OrderDetailsVO> listVo = new ArrayList<>();
            orderDetails.forEach(o -> {
                listVo.add(this.modelMapper.map(o, OrderDetailsVO.class));
            });
            return listVo;
        }
        return null;
    }

    @Override
    public void deleteOrderDetail(CartItemDTO dto) {
        CartDTO cartDTO = new CartDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth.getPrincipal().equals("anonymousUser")){
             cartDTO = (CartDTO) session.getAttribute("myCart");
            cartDTO.getListCartItem().remove(dto);

        }else{
            repository.deleteById(dto.getIdOrderDetail());
             cartDTO = cartService.findCart();

        }
        session.setAttribute("myCart",cartDTO);

    }

    @Override
    public void updateQuantityOrderDetail(Integer quan, Integer idDetail) {
        repository.updateQuantityOrderDetail(quan,idDetail);
    }

}

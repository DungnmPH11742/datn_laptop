package com.poly.service.impl;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CartItemDTO;
import com.poly.entity.OrderDetails;
import com.poly.entity.Orders;
import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;
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
import java.util.*;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailsRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private HttpSession session;

    @Override
    public OrderDetailsVO saveOderDetail(OrderDetailsVO orderDetailsVO) {
        OrderDetails orderDetails = modelMapper.map(orderDetailsVO,OrderDetails.class);
        ProductsDetail productsDetail = modelMapper.map(orderDetailsVO.getProductsDetailVO(),ProductsDetail.class);
        orderDetails.setProductsDetail(productsDetail);
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
        System.out.println("updateQuantityOrderDetail");
        repository.updateQuantityOrderDetail(quan,id);
    }

    @Override
    public Map<String, Object> deleteOrderDetail(CartItemDTO dto) {
        Map<String,Object> map = new HashMap<>();
        float sumPrice = 0;
        CartDTO cartDTO = new CartDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth.getPrincipal().equals("anonymousUser")){
            cartDTO = (CartDTO) cartService.findCart();

            //
            Iterator<CartItemDTO> itemDTOIterator = cartDTO.getListCartItem().iterator();
            while (itemDTOIterator.hasNext()){
                CartItemDTO itemDTO = itemDTOIterator.next();
                if(itemDTO.getSku().equals(dto.getSku())){
                    sumPrice = cartDTO.getTotalPrice() - itemDTO.getQuantityProduct() * itemDTO.getPriceSale();
                    itemDTOIterator.remove();
                }
            }


        }else{
            repository.deleteById(dto.getIdOrderDetail());
            cartDTO = cartService.findCart();
            sumPrice = cartDTO.getTotalPrice();

        }
        map.put("sumPrice",sumPrice);
        session.setAttribute("myCart",cartDTO);
        return map;
    }

}

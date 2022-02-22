package com.poly.service;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CartItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CartService {
    Map<String, Object> addTocart(CartItemDTO cartItemDTO);

    CartDTO findCart();

    void addCartFromSession();

    void transferFromOrderUpSession();

    List<CartItemDTO> getListCartItemOrder(Integer idOrder);

}

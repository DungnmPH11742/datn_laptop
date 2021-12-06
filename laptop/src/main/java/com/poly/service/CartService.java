package com.poly.service;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CartItemDTO;

import java.util.List;
import java.util.Map;

public interface CartService {
     Map<String, Object> addTocart(CartItemDTO cartItemDTO);

    CartDTO findCart();

}

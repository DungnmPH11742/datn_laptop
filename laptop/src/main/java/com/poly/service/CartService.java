package com.poly.service;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CartItemDTO;
<<<<<<< HEAD
=======
import org.springframework.stereotype.Service;
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06

import java.util.List;
import java.util.Map;

<<<<<<< HEAD
public interface CartService {
     Map<String, Object> addTocart(CartItemDTO cartItemDTO);

    CartDTO findCart();

=======
@Service
public interface CartService {
        Map<String, Object> addTocart(CartItemDTO dto);

        List<CartItemDTO> findAllCart();
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
}

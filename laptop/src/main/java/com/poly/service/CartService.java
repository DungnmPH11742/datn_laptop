package com.poly.service;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CartItemDTO;
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
import org.springframework.stereotype.Service;
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2

import java.util.List;
import java.util.Map;

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2
public interface CartService {
     Map<String, Object> addTocart(CartItemDTO cartItemDTO);

    CartDTO findCart();

<<<<<<< HEAD
=======
=======
@Service
public interface CartService {
        Map<String, Object> addTocart(CartItemDTO dto);

        List<CartItemDTO> findAllCart();
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2
}

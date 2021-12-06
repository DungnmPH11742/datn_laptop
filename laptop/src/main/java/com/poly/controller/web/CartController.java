package com.poly.controller.web;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CartItemDTO;
import com.poly.service.CartService;
import com.poly.service.ProductService;
import com.poly.service.SaleProductService;
import com.poly.vo.ProductsVO;
import com.poly.vo.SaleProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {
    @Autowired private  HttpSession session;

    @Autowired
    private ProductService productService;
    @Autowired
    private SaleProductService saleProductService;

    @Autowired private CartService cartService;

    @GetMapping(value = "/cart")
    public String cartIndex(Model model, HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession();
        List<CartItemDTO> list = new ArrayList<>();
<<<<<<< HEAD
        CartDTO cartDTO = cartService.findCart();
        session.setAttribute("myCart",cartDTO);
=======
        list = cartService.findAllCart();

>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
        return "user/cart";
    }

    @PostMapping(value = "/addToCart")
    public ResponseEntity<Map<String,Object>> addToCart(@RequestBody CartItemDTO cartItemDTO, HttpServletRequest request){

        Map<String,Object> map = cartService.addTocart(cartItemDTO);
        return ResponseEntity.ok(map);
    }
}

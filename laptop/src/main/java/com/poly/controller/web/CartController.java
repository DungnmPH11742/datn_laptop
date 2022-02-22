package com.poly.controller.web;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CartItemDTO;
import com.poly.helper.HeaderHelper;
import com.poly.service.CartService;
import com.poly.service.OrderDetailService;
import com.poly.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private OrderDetailService detailService;

    @Autowired private CartService cartService;

    @Autowired
    private HeaderHelper headerHelper;

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/cart")
    public String cartIndex(Model model, HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        session = request.getSession();
        CartDTO cartDTO = new CartDTO();
        if(session.getAttribute("myCart") != null){
            session.setAttribute("myCart",cartService.findCart());
        }
        headerHelper.setHeaderSession(model);
        return "user/cart";
    }

    @ResponseBody
    @PutMapping(value = "/addToCart")
    public ResponseEntity<Map<String,Object>> addToCart(@RequestBody CartItemDTO cartItemDTO, HttpServletRequest request){
        System.out.println("addToCart: " + cartItemDTO);
        Map<String,Object> map = cartService.addTocart(cartItemDTO);
        return ResponseEntity.ok(map);
    }

    @ResponseBody
    @PostMapping(value = "/cart-delete-orderdetail")
    public ResponseEntity<Map<String,Object>> deletecart(@RequestBody String sku){
        String skuNew = sku.replaceAll("\"","");
        Map<String,Object> map = detailService.deleteOrderDetail(skuNew);
        return ResponseEntity.ok(map);
    }

    @ResponseBody
    @PostMapping(value = "/cart-delete-all")
    public ResponseEntity<Map<String,Object>> deletecartAll(){
        Map<String,Object> map = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(session.getAttribute("myCart")!= null){
            CartDTO cartDTO = (CartDTO) session.getAttribute("myCart");
            if(auth == null || auth.getPrincipal().equals("anonymousUser")){
            }else {
                orderService.deleteById(cartDTO.getIdOrder());
            }
            map.put("messageDel","Xóa thành công!!");
        }else{
            map.put("messageDel","Không có sản phẩm nào để xóa");
        }

        session.setAttribute("myCart",null);
        return ResponseEntity.ok(map);
    }
}

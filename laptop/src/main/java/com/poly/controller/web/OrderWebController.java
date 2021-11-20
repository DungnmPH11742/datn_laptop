package com.poly.controller.web;

import com.poly.service.OrderService;
import com.poly.service.ProductService;
import com.poly.vo.OrderDetailsVO;
import com.poly.vo.OrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderWebController {
    @Autowired private OrderService orderService;
    @Autowired private HttpServletRequest request;
    @Autowired private ProductService productService;

    @RequestMapping("/orderproductdetail")
    public String index(Model model){
        model.addAttribute("listoderproduct",orderService.findOrdersByAccount(1));
        return "user/order-detail-user";
    }

    @RequestMapping("/order")
    public String order(){
        return "user/order";
    }

    @RequestMapping("/carttest")
    public String test(){
        return "user/cart";
    }


    @RequestMapping("/cart")
    public String indexCart(Model model){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        List<OrderDetailsVO> list = new ArrayList<>();
//        // Khi chưa đăng nhập
//        if(auth ==null || auth.getPrincipal() =="anonymousUser"){
//            Cookie[] cookies = request.getCookies();
//            if(cookies != null){
//                for (Cookie cookie:cookies){
//                    System.out.println(cookie);
//                    OrderDetailsVO detailsVO = new OrderDetailsVO();
//                    detailsVO.setProductsVO(productService.getOne(cookie.getName()));
//                    detailsVO.setQuantity(Integer.parseInt(cookie.getValue()));
//                    list.add(detailsVO);
//                }
//            }
//        }else{ // Đã đăng nhập
//
//        }
//        model.addAttribute("listcart",list);
        return "user/cart";
    }


}

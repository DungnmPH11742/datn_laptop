package com.poly.controller.api;

import com.poly.config.CookieService;
import com.poly.entity.Orders;
import com.poly.service.OrderService;
import com.poly.service.ProductService;
import com.poly.vo.OrderDetailsVO;
import com.poly.vo.OrdersVO;
import com.poly.vo.ProductsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired private HttpServletRequest request;
    @Autowired
    private CookieService cookieService;
    @RequestMapping("/listorderaccount")
    public List<OrdersVO> listAllByAccount(@RequestParam("id") Integer id){
        return orderService.findOrdersByAccount(id);
    }

    @RequestMapping("/orderdetailid")
    public OrdersVO findById(@RequestParam("id") Integer id){
        System.out.println(orderService.findidOrder(id));
        return orderService.findidOrder(id);
    }

    @RequestMapping("/addcart")
    public Boolean addToCart(@RequestParam("id") String id){
        System.out.println("/addcart - id:" + id);
        Boolean check = false;
        ProductsVO productsVO = productService.getOne(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Nếu chưa đăng nhập
        if(productsVO != null && productsVO.getQuantity()>0){

            if(auth ==null || auth.getPrincipal() =="anonymousUser"){
                Cookie cookie = cookieService.get(id);
                if(cookie == null){
                    cookieService.add(id,1,12);
                    System.out.println("addCart");
                }else{
                    System.out.println("UpdateCart");
                    System.out.println();
                    cookieService.add(id,Integer.parseInt(cookie.getValue()) + 1,12);

                }

            }else{ // Đã đăng nhập

            }
            return true;
        }
        return false;
    }

    @RequestMapping("/listcart")
    public List<OrderDetailsVO> getListCart(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<OrderDetailsVO> list = new ArrayList<>();
        // Khi chưa đăng nhập
        if(auth ==null || auth.getPrincipal() =="anonymousUser"){
            Cookie[] cookies = request.getCookies();
            System.out.println(cookies);
            if(cookies != null){
                for (Cookie cookie:cookies){
                    System.out.println(cookie);
                    OrderDetailsVO detailsVO = new OrderDetailsVO();
                    detailsVO.setProductsVO(productService.getOne(cookie.getName()));
                    detailsVO.setQuantity(Integer.parseInt(cookie.getValue()));
                    list.add(detailsVO);
                }
            }
        }else{ // Đã đăng nhập

        }
        return list;
    }

    @RequestMapping("/listcookie")
    public Cookie[] cookieList(){
        Cookie[] list = request.getCookies();
        return  list;
    }

}

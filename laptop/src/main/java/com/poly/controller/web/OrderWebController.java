package com.poly.controller.web;

import com.poly.helper.HeaderHelper;
import com.poly.service.AccountService;
import com.poly.service.OrderService;
import com.poly.service.ProductService;
import com.poly.vo.AccountVO;
import com.poly.vo.OrderDetailsVO;
import com.poly.vo.OrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderWebController {
    @Autowired private OrderService orderService;
    @Autowired private HttpServletRequest request;
    @Autowired private ProductService productService;
    @Autowired private HeaderHelper headerHelper;
    @Autowired private AccountService accountService;

    @RequestMapping("/orderproductdetail")
    public String index(Model model){

        model.addAttribute("listoderproduct",orderService.findOrdersByAccount(1));
        return "user/order-detail-user";
    }
    @RequestMapping("/order-detail/{id}")
    public String index(Model model, @PathVariable("id") Integer id){
        headerHelper.setHeaderSession(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")){
            String name = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(name);
            OrdersVO ordersVO = this.orderService.findOrderByIdAndAccount(id,accountVO.getId());

            if (ordersVO!=null){
                float totalPrice = 0;
                float totalPriceOutPut = 0;
                for (OrderDetailsVO x:ordersVO.getOrderDetails()) {
                    totalPrice += x.getPrice();
                }
                for (int i=0;i<ordersVO.getOrderDetails().size();i++) {
//                    totalPriceOutPut+=ordersVO.getOrderDetails().get(i).getProduct().getOutputPrice();
                }
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = ordersVO.getOrderDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DAY_OF_WEEK,7);
                String estimated = df.format(cal.getTime());
                Integer nameInt = ordersVO.getAddress().lastIndexOf('-');
                String nameUser = ordersVO.getAddress().substring(0,nameInt);
                String address = ordersVO.getAddress().substring(nameInt+1);
                model.addAttribute("estimatedTime",estimated);
                model.addAttribute("nameUser",nameUser.trim());
                model.addAttribute("addressUser",address.trim());
                model.addAttribute("totalPriceOutPut",totalPriceOutPut);
                model.addAttribute("totalPrice",totalPrice);
                model.addAttribute("orderDetail",ordersVO);
                return "user/order-detail-user";
            }else {
                //Cái này phải redirect sang 404
                return "redirect:/error-page";
            }
        }else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/cancel-order/{id}")
    public ResponseEntity<Map<String,Object>> cancelOrder(@PathVariable("id") Integer id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String,Object> map = new HashMap<>();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String name = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(name);
            OrdersVO vo = this.orderService.findOrderByIdAndAccount(id,accountVO.getId());
            if (!vo.isPaymentStatus()){
                vo.setReceived(-1);
                this.orderService.updateOrders(vo);
                map.put("data",vo);
            }
            return ResponseEntity.ok(map);
        }
        return null;

    }
    @RequestMapping("/list-order")
    public String order(Model model, HttpServletRequest request, @RequestParam("status") Optional<Integer> status){
        headerHelper.setHeaderSession(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")){
            String name = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(name);
            if (status.isPresent()){
                model.addAttribute("listOrder",orderService.findOrdersByAccountAndReceived(accountVO.getId(),status.get()));
            }else {
                model.addAttribute("listOrder",orderService.findOrdersByAccount(accountVO.getId()));
            }
            return "user/order";
        }else {
            return "redirect:/login";
        }


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

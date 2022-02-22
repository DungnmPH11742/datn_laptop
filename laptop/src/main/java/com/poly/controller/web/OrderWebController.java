package com.poly.controller.web;

import com.poly.DTO.CartItemDTO;
import com.poly.helper.HeaderHelper;
import com.poly.service.AccountService;
import com.poly.service.CartService;
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
import java.util.stream.Collectors;

@Controller
public class OrderWebController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ProductService productService;
    @Autowired
    private HeaderHelper headerHelper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CartService cartService;

    @RequestMapping("/orderproductdetail")
    public String index(Model model) {

        model.addAttribute("listoderproduct", orderService.findOrdersByAccount(1));
        return "user/order-detail-user";
    }

    @RequestMapping("/order-detail/{id}")
    public String index(Model model, @PathVariable("id") Integer id) {
        headerHelper.setHeaderSession(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String name = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(name);
            OrdersVO ordersVO = this.orderService.findOrderByIdAndAccount(id, accountVO.getId());

            if (ordersVO != null) {
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = ordersVO.getOrderDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DAY_OF_WEEK, 7);
                String estimated = df.format(cal.getTime());
                Integer nameInt = ordersVO.getAddress().lastIndexOf('-');
                String nameUser = ordersVO.getAddress().substring(0, nameInt);
                String address = ordersVO.getAddress().substring(nameInt + 1);
                model.addAttribute("estimatedTime", estimated);
                model.addAttribute("nameUser", nameUser.trim());
                model.addAttribute("addressUser", address.trim());
                model.addAttribute("order", ordersVO);
                return "user/order-detail-user";
            } else {
                //Cái này phải redirect sang 404
                return "redirect:/error-page";
            }
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/cancel-order/{id}")
    public ResponseEntity<Map<String, Object>> cancelOrder(@PathVariable("id") Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("ID: " + id);
        Map<String, Object> map = new HashMap<>();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String name = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(name);
            OrdersVO vo = this.orderService.findOrderByIdAndAccount(id, accountVO.getId());
            if (!vo.isPaymentStatus()) {
                vo.setReceived(-1);
                this.orderService.updateOrders(vo);
                map.put("data", vo);
            }
            return ResponseEntity.ok(map);
        }
        return null;

    }

    @RequestMapping("/list-order")
    public String order(Model model, HttpServletRequest request, @RequestParam("status") Optional<Integer> status,
                        @RequestParam("page") Optional<Integer> page) {
        headerHelper.setHeaderSession(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String name = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(name);
            List<OrdersVO> ordersVOList = this.orderService.findOrdersByAccount(accountVO.getId());
            float totalOrder = 0;
            int countOrder = ordersVOList.size();
            for (OrdersVO v : ordersVOList) {
                if (v.getTotalPrice() != null) {
                    totalOrder += v.getTotalPrice();
                }
            }
            model.addAttribute("totalOrder", totalOrder);
            model.addAttribute("countOrder", countOrder);
            Comparator<OrdersVO> comparing = Comparator
                    .comparing(OrdersVO::getReceived)
                    .reversed()
                    .thenComparing(OrdersVO::getId);
            if (status.isPresent()) {
                if (page.isPresent()) {
                    Map<String, Object> mapDefault = this.orderService.getOrderByReceivedPaging(accountVO.getId(), status.get(), page.get(), 10);
                    List<OrdersVO> listDefault = (List<OrdersVO>) mapDefault.get("listOrder");
                    model.addAttribute("totalPage", (Integer) mapDefault.get("totalPage"));
                    model.addAttribute("listOrder", listDefault);
                    model.addAttribute("currentPage", mapDefault.get("currentPage"));
                } else {
                    Map<String, Object> mapDefault = this.orderService.getOrderByReceivedPaging(accountVO.getId(), status.get(), 1, 10);
                    List<OrdersVO> listDefault = (List<OrdersVO>) mapDefault.get("listOrder");
                    model.addAttribute("totalPage", (Integer) mapDefault.get("totalPage"));
                    model.addAttribute("listOrder", listDefault);
                    model.addAttribute("currentPage", mapDefault.get("currentPage"));
                }
            } else {
                if (page.isPresent()) {
                    Map<String, Object> mapDefault = this.orderService.getAllOrderPaging(accountVO.getId(), page.get(), 10);
                    List<OrdersVO> listDefault = (List<OrdersVO>) mapDefault.get("listOrder");

                    listDefault.stream().sorted(comparing).collect(Collectors.toList());
                    model.addAttribute("totalPage", (Integer) mapDefault.get("totalPage"));
                    model.addAttribute("listOrder", listDefault);
                    model.addAttribute("currentPage", mapDefault.get("currentPage"));
                } else {
                    Map<String, Object> mapDefault = this.orderService.getAllOrderPaging(accountVO.getId(), 1, 10);
                    List<OrdersVO> listDefault = (List<OrdersVO>) mapDefault.get("listOrder");
                    listDefault.stream().sorted(comparing).collect(Collectors.toList());
                    model.addAttribute("totalPage", (Integer) mapDefault.get("totalPage"));
                    model.addAttribute("listOrder", listDefault);
                    model.addAttribute("currentPage", mapDefault.get("currentPage"));
                }
            }
            return "user/order";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/buy-again/{id}")
    public String buyAgain(@PathVariable("id") Integer id, Model model) {
        System.out.println(id);
        List<CartItemDTO> itemDtos = this.cartService.getListCartItemOrder(id);
        for (CartItemDTO c : itemDtos) {
            cartService.addTocart(c);
        }
        return "redirect:/cart";
    }
}

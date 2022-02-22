package com.poly.controller.api;

import com.poly.service.OrderDetailService;
import com.poly.service.OrderService;
import com.poly.vo.OrderDetailsVO;
import com.poly.vo.OrdersVO;
import com.poly.vo.response.OrderResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/admin")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/list-order/{received}")
    public ResponseEntity<List<OrderResponseVO>> findAllOrders(@PathVariable("received") Integer received){
        return ResponseEntity.ok(orderService.findAllOrdersByReceived(received));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<OrdersVO> findById(){
        return ResponseEntity.ok(orderService.findIdOrder(0));
    }


    @GetMapping("/find-by-name-account/{received}")
    public ResponseEntity<List<OrderResponseVO>> findByNameAccount(@PathVariable("received") Integer received, @RequestParam("name") String name){
        return ResponseEntity.ok(orderService.findByNameAccount(name, received));
    }

    @GetMapping("/find-order-dt-by-id/{id}")
    public ResponseEntity<OrderDetailsVO> findOrderDetailById(@PathVariable("id") int id) {
        return ResponseEntity.ok(orderDetailService.findById(id));
    }

    @PutMapping("/update-order")
    public ResponseEntity<OrdersVO> update(@RequestBody OrdersVO vo){
    return ResponseEntity.ok(orderService.updateOrders(vo));
    }
}

package com.poly.controller.api;

import com.poly.service.OrderService;
import com.poly.vo.OrdersVO;
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

    @GetMapping("/list-order/{received}")
    public ResponseEntity<List<OrdersVO>> findAllOrders(@PathVariable("received") Integer received){
        return ResponseEntity.ok(orderService.findAllOrdersByReceived(received));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<OrdersVO> findById(){
        return ResponseEntity.ok(orderService.findIdOrder(0));
    }

    @PutMapping("/update-order")
    public ResponseEntity<OrdersVO> update(@RequestBody OrdersVO vo){
    return ResponseEntity.ok(orderService.updateOrders(vo));
    }
}

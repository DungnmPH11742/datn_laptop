package com.poly.controller.api;


import com.poly.service.OrderDetailService;
import com.poly.vo.OrderDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/admin")
public class OrderDetailApiController {
    @Autowired
    private OrderDetailService orderDetailService;

    @PutMapping("update-order-detail")
    public ResponseEntity<OrderDetailsVO> update(@RequestBody OrderDetailsVO vo){
        return ResponseEntity.ok(orderDetailService.updateOrderDetail(vo));
    }
}

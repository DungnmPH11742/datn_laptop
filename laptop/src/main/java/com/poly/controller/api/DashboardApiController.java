package com.poly.controller.api;

import com.poly.service.SynthesisReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/admin")
public class DashboardApiController {

    @Autowired
    private SynthesisReportService synthesisReportService;

    @GetMapping("/orders-quantity-by-received")
    public ResponseEntity<Integer> ordersQuantityByReceived(@RequestParam("received") int received){
        return ResponseEntity.ok(synthesisReportService.ordersQuantityByReceived(received));
    }
}

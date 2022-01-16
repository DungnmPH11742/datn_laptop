package com.poly.controller.api;

import com.poly.service.SynthesisReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/report-by-product-type")
    public ResponseEntity<List<?>> reportByProductType(){
        return ResponseEntity.ok(synthesisReportService.reportByProductType());
    }

    @GetMapping("/top-best-selling-products")
    public ResponseEntity<List<?>> top5BestSellingProducts(){
        return ResponseEntity.ok(synthesisReportService.top5BestSellingProducts());
    }
}

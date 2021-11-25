package com.poly.controller.api;

import com.poly.service.SaleService;
import com.poly.vo.SaleProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class SaleProductApiController {

    @Autowired
    private SaleService saleService;

    @GetMapping("/find-all-sale")
    private ResponseEntity<List<SaleProductVO>> findAll(){
        return ResponseEntity.ok(saleService.findAll());
    }
}

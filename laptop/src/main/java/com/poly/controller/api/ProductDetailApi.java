package com.poly.controller.api;

import com.poly.service.impl.ProductDetailServiceImpl;
import com.poly.vo.ProductsDetailVO;
import com.poly.vo.response.ProductsReponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/admin")
public class ProductDetailApi {
    @Autowired
    private ProductDetailServiceImpl detailService;

    @GetMapping("/find-by-sku/{sku}")
    public ResponseEntity<ProductsReponseVO> findBySku(@PathVariable("sku") String sku) {
        return ResponseEntity.ok(detailService.findBySkuProduct(sku));
    }

    @GetMapping("/find-detail-by-sku/{sku}")
    public ResponseEntity<ProductsDetailVO> findDetailBySku(@PathVariable("sku") String sku) {
        return ResponseEntity.ok(detailService.findBySku(sku));
    }

    @PostMapping("/save-p-detail")
    public ResponseEntity<ProductsDetailVO> create(@RequestBody ProductsDetailVO vo) {
        return ResponseEntity.ok(detailService.save(vo));
    }

    @PutMapping("/update-d-detail")
    public ResponseEntity<ProductsDetailVO> update(@RequestBody ProductsDetailVO vo) {
        return ResponseEntity.ok(detailService.save(vo));
    }

    @DeleteMapping("/delete-d-detail/{sku}")
    public ResponseEntity<Boolean> delete(@PathVariable("sku") String sku) {
        return ResponseEntity.ok(detailService.delete(sku));
    }
}

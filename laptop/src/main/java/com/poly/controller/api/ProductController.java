package com.poly.controller.api;

import com.poly.service.ProductService;
import com.poly.service.VouchersService;
import com.poly.vo.ProductsVO;
import com.poly.vo.VouchersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private VouchersService vouchersService;
    @RequestMapping("/find-by")
    public ResponseEntity<List<ProductsVO>> findByNameContainingAndTypeOfItem(@RequestParam("name")String name, @RequestParam("type") int type) {
        productService.findByNameContainingAndTypeOfItem(name, type);
        return ResponseEntity.ok(productService.findByNameContainingAndTypeOfItem(name, type));
    }
    /*@RequestMapping(value = "/voucher/{id}")
    public ResponseEntity<VouchersVO> getVoucher(@PathVariable("id") String id){
        VouchersVO vo = this.vouchersService.getVoucherTrue(id);
        if (vo == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(vo);
    }*/


}

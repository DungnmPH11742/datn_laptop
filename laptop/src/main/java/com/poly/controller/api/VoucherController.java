package com.poly.controller.api;

import com.poly.DTO.CategorySelectOption;
import com.poly.DTO.ProductSelectOption;
import com.poly.DTO.VouchersDTO;
import com.poly.service.VouchersService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/admin/voucher")
public class VoucherController {
    @Autowired
    private VouchersService service;

    @GetMapping()
    public ResponseEntity<List<VouchersDTO>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }


    @GetMapping("/cate")
    public ResponseEntity<List<CategorySelectOption>> selectAllCate(){
        return ResponseEntity.ok(service.cateSelectOption());
    }

    @GetMapping("/pro")
    public ResponseEntity<List<ProductSelectOption>> selectAllPro(){
        return ResponseEntity.ok(service.productsSelectOption());
    }

    @PutMapping()
    public ResponseEntity<VouchersDTO> create(@RequestBody VouchersDTO dto){
        System.out.println("save");
        if(dto.getId() == null || dto.getId().equals("")){
            VouchersDTO dto1 = service.create(dto);
            return ResponseEntity.ok(dto1);
        }else if(dto.getId() != null || !dto.getId().equals("")){
            VouchersDTO dto1 = service.update(dto);
            return ResponseEntity.ok(dto1);
        }
        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id){
        if(!id.equals("") || id != null){
            return ResponseEntity.ok(service.delete(id));
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}

package com.poly.controller.api;

import com.poly.service.SaleService;
import com.poly.vo.SaleProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/admin/sale-admin")
public class SaleProductApiController {

    @Autowired
    private SaleService saleService;

    @GetMapping("/find-all-sale")
    private ResponseEntity<List<SaleProductVO>> findAll(){
        return ResponseEntity.ok(saleService.findAll());
    }

    @GetMapping()
    private ResponseEntity<List<SaleProductVO>> findAllSale(){
        return ResponseEntity.ok(saleService.findAll());
    }

    @PostMapping()
    public ResponseEntity<SaleProductVO> create(@RequestBody SaleProductVO saleProductVO){
        if(saleProductVO != null && (saleProductVO.getSaleCode()==null || saleProductVO.getSaleCode().equals(""))){
            SaleProductVO vo = saleService.save(saleProductVO);
            if(vo != null){
                return ResponseEntity.ok(vo);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }else{
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping()
    public ResponseEntity<SaleProductVO> update(@RequestBody SaleProductVO saleProductVO){
        if(saleProductVO.getSaleCode() != null || !saleProductVO.getSaleCode().equals("")){
            SaleProductVO vo = saleService.update(saleProductVO);
            return ResponseEntity.ok(vo);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/{salecode}")
    public ResponseEntity<Boolean> delete(@PathVariable("salecode") String saleCode){
        if(!saleCode.equals("")){
            return ResponseEntity.ok(saleService.updateStatusSale(saleCode));
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}

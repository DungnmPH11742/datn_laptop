package com.poly.controller.api;

import com.poly.repo.ProductsRepository;
import com.poly.service.ProductService;
import com.poly.vo.ProductsVO;
import com.poly.vo.request.ProductRequestVO;
import com.poly.vo.response.ProductsReponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:4200")
public class ProductAdminAPI {

    @Autowired
    private ProductService productService;

    @GetMapping("/product-find-all-sku")
    public ResponseEntity<List<ProductsReponseVO>> findAll() {
        return ResponseEntity.ok(productService.findAllSku());
    }


    @GetMapping(value = "/product/find-all")
    public ResponseEntity<List<ProductsVO>> getListProduct() {
        List<ProductsVO> voList = this.productService.getList();
        if (voList != null) {
            return ResponseEntity.ok(voList);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/product/find-all-not-del")
    public ResponseEntity<List<ProductsVO>> getListProductNotDel() {
        List<ProductsVO> voList = this.productService.findAllSkuNotDel();
        if (voList != null) {
            return ResponseEntity.ok(voList);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

        @GetMapping(value = "/product/{id}")
    public ResponseEntity<ProductsVO> getProduct(@PathVariable("id") String id) {
        ProductsVO productsVO = this.productService.getOne(id);
        if (productsVO != null) {
            return ResponseEntity.ok(productsVO);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/store-product")
    public ResponseEntity<ProductsVO> save(@RequestBody ProductRequestVO productsVO) {
//        if (productsVO.getProductsDetails() != null) {
            ProductsVO vo = this.productService.create(productsVO);
            if (vo != null) {
                return ResponseEntity.ok(vo);
            } else {
                //Sản phẩm đã có trong csdl
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
    }

    @PutMapping(value = "/update-product")
    public ResponseEntity<ProductsVO> update(@RequestBody ProductRequestVO productsVO) {
        ProductsVO productUpdate = this.productService.update(productsVO);
        System.err.println("Nè");
        System.err.println(productsVO);
        if (productUpdate != null) {
            return new ResponseEntity<>(productUpdate, HttpStatus.OK);
        } else {
            //Không có product trong database => return status 304
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/delete-product/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        Boolean checkDelete = this.productService.delete(id);
        if (checkDelete) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}

package com.poly.api;

import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;
import com.poly.repo.ProductsRepository;
import com.poly.service.ProductDetailService;
import com.poly.service.ProductService;
import com.poly.vo.ProductsDetailVO;
import com.poly.vo.ProductsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductDetailAdminAPI {

    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/admin/productDetail/get")
    public ResponseEntity<List<ProductsDetailVO>> getAll(){
        List<ProductsDetailVO> productsDetailVOS = this.productDetailService.getList();
        if (!productsDetailVOS.isEmpty()){
            return ResponseEntity.ok(productsDetailVOS);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/admin/product-detail/{id}")
    public ResponseEntity<ProductsDetailVO> getOneProductDetail(@PathVariable("id") String id){
        ProductsDetailVO productsDetailVO = this.productDetailService.getOne(id);
        if (productsDetailVO != null){
            return ResponseEntity.ok(productsDetailVO);
        }else {
            //304 -> khong tim thay
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

    }

    @PostMapping(value = "/admin/store-productDetail")
    public ResponseEntity<ProductsDetailVO> create(@RequestBody ProductsDetailVO productsDetailVO){
        Optional<Products> products = this.productsRepository.findById(productsDetailVO.getId());
        if (products.isPresent()){
            ProductsDetailVO productDetail = this.productDetailService.create(productsDetailVO);
            if (productDetail == null){
                //304 -> productDetail đã có trong database
                return new ResponseEntity<>( HttpStatus.NOT_MODIFIED);
            }else {
                return new ResponseEntity<>(productsDetailVO, HttpStatus.OK);
            }
        }else {
            //400 -> Không tìm thấy products nào
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/admin/update-productDetail")
    public ResponseEntity<ProductsDetailVO> update(@RequestBody ProductsDetailVO productsDetailVO){
        ProductsDetailVO productsDetail = this.productDetailService.update(productsDetailVO);
        if (productsDetail != null){
            return new ResponseEntity<>(productsDetail,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}

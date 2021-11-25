package com.poly.controller.api;

import com.poly.service.CategoryService;
import com.poly.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class CategoryApiController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/cate-find-all")
    public ResponseEntity<List<CategoryVO>> findAll(){
        List<CategoryVO> vos = categoryService.getList();
        if(vos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            return ResponseEntity.ok(vos);
        }
    }

    @GetMapping("/cate-find")
    public ResponseEntity<List<CategoryVO>> findAllByParentId(@RequestParam("parent_id")Integer id){
        List<CategoryVO> vos = categoryService.getListByParent(id);
        if(vos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            return ResponseEntity.ok(vos);
        }
    }

    @GetMapping("/node-cate")
    public ResponseEntity<List<CategoryVO>> getNodeCate(){
        List<CategoryVO> vos = categoryService.getNodeCate();
        if(vos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            return ResponseEntity.ok(vos);
        }
    }

}

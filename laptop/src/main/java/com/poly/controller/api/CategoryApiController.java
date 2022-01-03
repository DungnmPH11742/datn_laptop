package com.poly.controller.api;

import com.poly.service.CategoryService;
import com.poly.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/admin")
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
    public ResponseEntity<List<CategoryVO>> findAllByParentId(@RequestParam("parent_id")String id){
        List<CategoryVO> vos = categoryService.getListByParent(id);
        if(vos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            return ResponseEntity.ok(vos);
        }
    }

    @GetMapping("/cate-find-by-id/{id}")
    public ResponseEntity<CategoryVO> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(categoryService.getOne(id));
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

    @PostMapping("/add-cate")
    public ResponseEntity<CategoryVO> save(@RequestBody CategoryVO vo){
        return ResponseEntity.ok(categoryService.create(vo));
    }

    @PutMapping("/update-cate")
    public ResponseEntity<?> update(@RequestBody CategoryVO vo) {
        CategoryVO categoryVO = categoryService.update(vo);
        if(categoryVO!=null){
            return ResponseEntity.ok(categoryVO);
        }else{
            return null;
        }
    }

}

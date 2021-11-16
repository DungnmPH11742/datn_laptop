package com.poly.controller.api;

import com.poly.service.BlogService;
import com.poly.vo.BlogsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogControllerAPI {

    @Autowired
    private BlogService blogService;

    @GetMapping(value = "/admin/blog/getOne/{id}")
    public ResponseEntity<BlogsVO> getOneBlog(@PathVariable("id") String id){
        try {
            BlogsVO vo = this.blogService.getOneBlog(id);
            if (vo != null){
                return ResponseEntity.ok(vo);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/admin/blog/save")
    public ResponseEntity<BlogsVO> saveBlog(@RequestBody BlogsVO blogsVO){
        BlogsVO vo = this.blogService.create(blogsVO);
        if (vo != null){
            return new ResponseEntity<>(vo, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping(value = "/admin/blog/update")
    public ResponseEntity<BlogsVO> updateBlog(@RequestBody BlogsVO vo){
        BlogsVO blogsVO = this.blogService.update(vo);
        if (blogsVO != null){
            return new ResponseEntity<>(blogsVO,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/admin/blog/delete/{title}")
    public ResponseEntity<BlogsVO> deleteBlog(@PathVariable("title") String title){
        BlogsVO blogsVO = this.blogService.delete(title);
        if (blogsVO != null){
            return ResponseEntity.ok(blogsVO);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}

package com.poly.controller.api;

import com.poly.repo.AccountRepository;
import com.poly.service.AccountService;
import com.poly.service.BlogService;
import com.poly.vo.BlogsVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/admin")
public class BlogApiController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/blog-all")
    public ResponseEntity<List<BlogsVO>> findAll() {
        return ResponseEntity.ok(blogService.findAll());
    }

    @GetMapping("/exit-blog/{id}")
    public ResponseEntity<BlogsVO> getExits(@PathVariable("id") String id) {
        return ResponseEntity.ok(blogService.getOneBlog(id));
    }

    @PostMapping("/save-blog")
    public ResponseEntity<BlogsVO> creat(@RequestBody BlogsVO vo) {
        return ResponseEntity.ok(blogService.create(vo));
    }

    @PutMapping("/update-blog")
    public ResponseEntity<BlogsVO> update(@RequestBody BlogsVO vo) {
        return ResponseEntity.ok(blogService.update(vo));
    }
}

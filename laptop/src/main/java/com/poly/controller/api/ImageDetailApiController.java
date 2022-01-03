package com.poly.controller.api;

import com.poly.service.ImageDetailService;
import com.poly.vo.ImageDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/admin")
public class ImageDetailApiController {
    @Autowired
    private ImageDetailService imageDetailService;

    @PostMapping("/save-img-detail")
    public ResponseEntity<ImageDetailVO> save(@RequestBody ImageDetailVO vo){
        return ResponseEntity.ok(imageDetailService.save(vo));
    }

    @PutMapping("/save-img-detail")
    public ResponseEntity<ImageDetailVO> update(@RequestBody ImageDetailVO vo){
        return ResponseEntity.ok(imageDetailService.update(vo));
    }
}

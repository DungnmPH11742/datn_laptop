package com.poly.controller.api;

import com.poly.service.DescriptionService;
import com.poly.vo.DescriptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:4200/")
public class DescriptionAdminAPI {

    @Autowired
    private DescriptionService descriptionService;

    @GetMapping(value = "/description-product/{sku}")
    public ResponseEntity<List<DescriptionVO>> getByProduct(@PathVariable("sku") String sku) {
        try {
            List<DescriptionVO> listVo = this.descriptionService.getDescriptionBySku(sku);
            if (listVo != null) {
                return ResponseEntity.ok(listVo);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/description-blog/{idBlog}")
    public ResponseEntity<List<DescriptionVO>> getListDescriptionByBlog(@PathVariable("idBlog") String idBlog) {
        try {
            List<DescriptionVO> listVo = this.descriptionService.getDescriptionByBlog(idBlog);
            return ResponseEntity.ok(listVo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/descriptionGetById/{id}")
    public ResponseEntity<DescriptionVO> getOneById(@PathVariable("id") Integer id) {
        try {
            DescriptionVO descriptionVO = this.descriptionService.getOneDescriptionById(id);
            if (descriptionVO != null) {
                return new ResponseEntity<>(descriptionVO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/description/save")
    public ResponseEntity<DescriptionVO> createDescription(@RequestBody DescriptionVO descriptionVO) {
        try {
            DescriptionVO descriptionVO1 = this.descriptionService.create(descriptionVO);
            if (descriptionVO1 == null) {
                System.err.println(descriptionVO);
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            } else {
                System.err.println(1);
                return ResponseEntity.ok(descriptionVO);
            }
        } catch (Exception e) {
            System.err.println(0);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/description/update/{id}")
    public ResponseEntity<DescriptionVO> updateDescription(@RequestBody DescriptionVO descriptionVO,
                                                           @PathVariable("id") Integer id) {
        try {
            DescriptionVO vo = this.descriptionService.update(descriptionVO, id);
            if (vo != null) {
                return ResponseEntity.ok(vo);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/description/delete/{id}")
    public ResponseEntity<DescriptionVO> deleteDescription(@PathVariable("id") Integer id) {
        try {
            DescriptionVO vo = this.descriptionService.delete(id);
            if (vo != null) {
                return ResponseEntity.ok(vo);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.poly.controller.api;

import com.poly.helper.CompareHelper;
import com.poly.helper.MessageHelper;
import com.poly.service.ProductService;
import com.poly.vo.ProductsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class CompareController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private CompareHelper compareHelper;

    @Autowired
    private ProductService productService;

    @RequestMapping("/add-compare")
    public MessageHelper addCompareProduct(@RequestParam("id") String id){
        return compareHelper.setProductVo(productService.getOne(id));
    }

    @RequestMapping("/get-compare")
    public List<ProductsVO> getCompareProduct(){
        request.setAttribute("list_compare", compareHelper.getAllProductVo());
        return compareHelper.getAllProductVo();
    }

    @RequestMapping("/remove-compare")
    public boolean removeCompare(@RequestParam("key")String key){
        return compareHelper.removeCompare(key);
    }


}

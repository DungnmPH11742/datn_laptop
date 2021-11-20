package com.poly.controller.web;

import com.poly.service.CategoryService;
import com.poly.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CategoryWebController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list-cate")
    public String getList(Model model) {
//        List<CategoryVO> list_cate = categoryService.getList();
//        model.addAttribute("list_cate", list_cate);
//        list_cate.forEach(categoryVO -> {
//            if (categoryVO.getParentId() == null) {
//                System.out.println(categoryVO.getName());
//                list_cate.forEach(categoryVO1 -> {
//                    if(categoryVO.getId() == categoryVO1.getParentId()){
//                        System.out.println("===>" + categoryVO1.getName());
//                            list_cate.forEach(categoryVO2 -> {
//                                if(categoryVO1.getId()==categoryVO2.getParentId()){
//                                    System.out.println("    ===>" + categoryVO2.getName());
//                                }
//                            });
//                    }
//                });
//            }
//        });
        return "common/header";
    }
}

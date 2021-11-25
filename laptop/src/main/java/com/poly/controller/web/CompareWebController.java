package com.poly.controller.web;

import com.poly.helper.CompareHelper;
import com.poly.helper.HeaderHelper;
import com.poly.vo.ProductsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CompareWebController {

    @Autowired
    private CompareHelper compareHelper;

    @Autowired
    private HeaderHelper headerHelper;

    @RequestMapping("/compare")
    public String viewCompare(Model model) {
        boolean check = false;
        List<ProductsVO> vos = compareHelper.getAllProductVo();
        for (int i = 0; i < vos.size(); i++) {
            if (vos.get(i) != null) {
                check = true;
                break;
            }
        }
        headerHelper.setHeaderSession(model);

        return check ? "user/compare" : "redirect:/home";
    }
}

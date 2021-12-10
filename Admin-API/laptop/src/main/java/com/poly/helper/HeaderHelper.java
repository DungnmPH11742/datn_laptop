package com.poly.helper;

import com.poly.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class HeaderHelper {

    @Autowired
    private CategoryService categoryService;

    public void setHeaderSession(Model model) {
        model.addAttribute("cate_all", categoryService.getList());
        model.addAttribute("cate_lt", categoryService.getListByParent(1));
        model.addAttribute("cate_pc", categoryService.getListByParent(57));
        model.addAttribute("cate_mo", categoryService.getListByParent(76));
    }
}

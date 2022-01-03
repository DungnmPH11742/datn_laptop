package com.poly.helper;

import com.poly.service.AccountService;
import com.poly.service.CategoryService;
import com.poly.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class HeaderHelper {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AccountService accountService;

    public void setHeaderSession(Model model) {
        model.addAttribute("cate_all", categoryService.getList());
        model.addAttribute("cate_lt", categoryService.getListByParent("LT01"));
        model.addAttribute("cate_pc", categoryService.getListByParent("PC01"));
        model.addAttribute("cate_mo", categoryService.getListByParent("MN01"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")){
            String name = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(name);
            model.addAttribute("account",accountVO);
            System.err.println(accountVO);
        }
    }
}

package com.poly.controller.web;

import com.poly.helper.HeaderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ContactWebController {

    @Autowired
    private HeaderHelper headerHelper;

    @RequestMapping(value = "/contact",method = RequestMethod.GET)
    public String showContact(Model model) {

        headerHelper.setHeaderSession(model);

        return "user/contact";
    }


}

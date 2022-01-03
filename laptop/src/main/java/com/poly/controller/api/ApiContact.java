package com.poly.controller.api;

import com.poly.service.ContactService;
import com.poly.vo.ContactVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiContact {
    @Autowired
    private ContactService contactService;

    @PostMapping("/contact")
    public ContactVO createContact(@RequestBody ContactVO contactVO)
    {
        return contactService.create(contactVO);
    }
}

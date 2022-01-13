package com.poly.DTO;

import com.poly.entity.Role;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;

@RestController
@CrossOrigin("http://localhost:4200/")
public class TestTiThoiController {

    private TestTiThoi testTiThoi = new TestTiThoi();
    private TestTiThoi testTiThoiOld = new TestTiThoi();
    Role role = new Role();

    @GetMapping("/abc")
    public TestTiThoi abc() {
        testTiThoi.getRoles().add(new Role(1, "abc"));
        role.setId(testTiThoi.getRoles().get(0).getId());
        role.setRoleName(testTiThoi.getRoles().get(0).getRoleName());
        testTiThoiOld.setRoles(new ArrayList<>(Collections.singleton(role)));
        return testTiThoiOld;
    }

    @GetMapping("/abcd")
    public TestTiThoi abd() {
        testTiThoi.getRoles().get(0).setRoleName("abcs");
        return testTiThoiOld;
    }
}

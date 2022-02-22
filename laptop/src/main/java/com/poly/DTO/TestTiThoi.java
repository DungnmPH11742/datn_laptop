package com.poly.DTO;

import com.poly.entity.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestTiThoi {
    private List<Role> roles = new ArrayList<>();
}

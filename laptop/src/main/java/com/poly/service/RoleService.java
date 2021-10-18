package com.poly.service;

import com.poly.entity.Role;
import com.poly.vo.RoleVO;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface RoleService {
    List<RoleVO> getAllRole();

    Role saveRoles(RoleVO roleVO);

    Role updateRoles(RoleVO roleVO);

    Role findByEmail(String email);
}

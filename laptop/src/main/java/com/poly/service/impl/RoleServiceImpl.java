package com.poly.service.impl;

import com.poly.entity.Role;
import com.poly.repo.RoleRepository;
import com.poly.service.RoleService;
import com.poly.vo.RoleVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RoleVO> getAllRole() {
        List<RoleVO> vo = new ArrayList<>();
        roleRepository.findAll().forEach(
                roles -> {
                    vo.add(modelMapper.map(roles, RoleVO.class));
                }
        );
        return vo;
    }

    @Override
    public Role saveRoles(RoleVO roleVO) {
        return null;
    }

    @Override
    public Role updateRoles(RoleVO roleVO) {
        return null;
    }

    @Override
    public com.poly.entity.Role findByEmail(String email) {
        return roleRepository.findByRoleName(email);
    }
}

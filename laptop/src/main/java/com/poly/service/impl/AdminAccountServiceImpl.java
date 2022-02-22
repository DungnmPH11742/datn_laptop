package com.poly.service.impl;

import com.poly.DTO.AccountDTO;
import com.poly.entity.Account;
import com.poly.entity.AccountRole;
import com.poly.entity.Role;
import com.poly.repo.AccountRepository;
import com.poly.repo.AccountRoleRepository;
import com.poly.repo.RoleRepository;
import com.poly.service.AdminAccountService;
import com.poly.vo.RoleVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AdminAccountServiceImpl  implements AdminAccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AccountDTO> findAllAccount() {
        List<AccountDTO> dtos = new ArrayList<>();
        repository.findAllByActived().forEach(account -> {
            AccountDTO dto = modelMapper.map(account,AccountDTO.class);
            List<RoleVO> vos = new ArrayList<>();
            account.getRoles().forEach(role -> {
                RoleVO roleVO = modelMapper.map(role,RoleVO.class);
                vos.add(roleVO);
            });
            dto.setRole(vos);
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public AccountDTO findByIdA(Integer id) {
        AccountDTO dto =  modelMapper.map(repository.findById(id).get(),AccountDTO.class);
        return dto;
    }

    @Override
    public AccountDTO createA(AccountDTO dto) {
        Account account = new Account();
        modelMapper.map(dto,account);
        account.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        account.setFullName(dto.getFullName());
        account.setSex(dto.getSex());
        account.setEmail(dto.getEmail());
        account.setPhone(dto.getPhone());
        account.setActived(true);
        account.setDateOfBirth(dto.getDateOfBirth());
        dto.setId(repository.save(account).getId());
        // Thêm vai trò
        dto.getRole().forEach(roleVO -> {
            AccountRole role = new AccountRole();
            role.setIdAccount(dto.getId());
            role.setIdRole(roleVO.getId());
            accountRoleRepository.save(role);
        });

        return dto;
    }

    @Override
    public AccountDTO updateA(AccountDTO dto) {
        Account account = new Account();
        modelMapper.map(dto,account);
        if(repository.getById(dto.getId()).getPassword().equals(account.getPassword())){
            account.setPassword(repository.getById(dto.getId()).getPassword());
        }else{
            account.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }
        repository.save(account).getId();
        // Thêm vai trò
        dto.getRole().forEach(roleVO -> {
            AccountRole role = new AccountRole();
            role.setIdAccount(dto.getId());
            role.setIdRole(roleVO.getId());
            accountRoleRepository.save(role);
        });
        return dto;
    }

    @Override
    public AccountDTO deleteA(AccountDTO dto) {
        Account account = new Account();
        dto.setActived(false);
        modelMapper.map(dto,account);
        repository.save(account);
        return dto;
    }
}

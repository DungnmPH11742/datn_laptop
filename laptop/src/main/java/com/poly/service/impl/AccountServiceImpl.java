package com.poly.service.impl;

import com.poly.entity.Account;
import com.poly.entity.Role;
import com.poly.repo.AccountRepository;
import com.poly.repo.ProductsRepository;
import com.poly.repo.RoleRepository;
import com.poly.service.AccountService;
import com.poly.service.ProductService;
import com.poly.vo.AccountVO;
import com.poly.vo.ProductsVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AccountVO> getList() {
        List<AccountVO> vos = new ArrayList<>();
        repository.findAll().forEach(accouts -> {
            vos.add(modelMapper.map(accouts, AccountVO.class));
        });
        return vos;
    }

    @Override
    public Account createAccountUser(AccountVO accountVO) {
        Account account = new Account();
        account.setPassword(bCryptPasswordEncoder.encode(accountVO.getPassword()));
        account.setFullName(accountVO.getFullName());
        account.setEmail(accountVO.getEmail());
        account.setPhone(accountVO.getPhone());
        account.setActived(accountVO.getActived());
        account.setDateOfBirth(accountVO.getDateOfBirth());
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByRoleName(accountVO.getNameRoles()));
        return repository.save(account);
    }
    @Override
    public Account updateAccountUser(AccountVO accountVO) {
        Account account = new Account();
        account.setPassword(bCryptPasswordEncoder.encode(accountVO.getPassword()));
        account.setFullName(accountVO.getFullName());
        account.setEmail(accountVO.getEmail());
        account.setPhone(accountVO.getPhone());
        account.setActived(accountVO.getActived());
        account.setDateOfBirth(accountVO.getDateOfBirth());
        return repository.save(account);
    }
    @Override
    public void deleteAccountById(Integer id) {
        repository.deleteById(id);
    };

    @Override
    public Account findByEmail(String email){
        return repository.findByEmail(email);
    }
}

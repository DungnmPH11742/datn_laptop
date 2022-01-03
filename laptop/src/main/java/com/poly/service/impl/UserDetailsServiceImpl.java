package com.poly.service.impl;

import com.poly.entity.Account;
import com.poly.entity.Role;
import com.poly.repo.AccountRepository;
import com.poly.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account appUser = this.accountRepository.findUserAccount(userName);
        if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }


        List<Role> roleNames = roleRepository.getRoleNames(appUser.getId());
        System.out.println(roleNames.size());
        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNames != null) {
            for (Role userRole : roleNames) {
                GrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRoleName());
                System.out.println(authority);
                grantList.add(authority);
            }
        }
        UserDetails userDetails = new User(appUser.getEmail(),
                appUser.getPassword(), grantList);
        return userDetails;
    }
}

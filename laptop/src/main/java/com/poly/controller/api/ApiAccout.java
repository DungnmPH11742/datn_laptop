package com.poly.controller.api;

import com.poly.config.JWTTokenProvider;
import com.poly.entity.Account;
import com.poly.entity.UserPrincipal;
import com.poly.service.AccountService;
import com.poly.service.impl.UserDetailsServiceImpl;
import com.poly.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200/")
public class ApiAccout {
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<AccountVO> login(@RequestBody Account account) {
        authenticate(account.getEmail(), account.getPassword());
        AccountVO loginAccount = accountService.findByEmailUser(account.getEmail());
        UserDetails userDetails  = userDetailsService.loadUserByUsername(account.getEmail());
        HttpHeaders jwtHeader = getJwtHeader(userDetails);
        return new ResponseEntity<>(loginAccount, jwtHeader, OK);
    }
    private HttpHeaders getJwtHeader(UserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Jwt-Token", jwtTokenProvider.generateJwtToken(userDetails));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}

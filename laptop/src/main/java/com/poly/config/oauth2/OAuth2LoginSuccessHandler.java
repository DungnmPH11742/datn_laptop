package com.poly.config.oauth2;

import com.poly.entity.Account;
import com.poly.entity.AuthenticationProvider;
import com.poly.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired private AccountService accountService;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        System.out.println("Oauth2 name la: "+ customOAuth2User.getName());
        String email = customOAuth2User.getEmail();
        String name = customOAuth2User.getHoTen();
        Account account = modelMapper.map(accountService.findByEmailUser(email), Account.class);
        if(account == null ){
           if(customOAuth2User.getId() == null && customOAuth2User.getSub() != null){
             accountService.createNewCustomerAfterOAuthLoginSuccess(email,name, AuthenticationProvider.GOOGLE);
           }
           else{
               accountService.createNewCustomerAfterOAuthLoginSuccess(email,name,AuthenticationProvider.FACEBOOK);
        }
    }
        else{
            if(customOAuth2User.getId() == null && customOAuth2User.getSub() != null)  {
                accountService.upadteCustomerAfterOAuthLoginSuccess(account, name, AuthenticationProvider.GOOGLE);
            }
            else {
                accountService.upadteCustomerAfterOAuthLoginSuccess(account, name,AuthenticationProvider.FACEBOOK);
            }
        }

        redirectStrategy.sendRedirect(request, response, "/home");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

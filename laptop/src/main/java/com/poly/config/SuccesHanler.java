package com.poly.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poly.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SuccesHanler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStra = new DefaultRedirectStrategy();

    @Autowired
    private CartService cartService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> author = authentication.getAuthorities();
        author.forEach(a -> {

            if (a.getAuthority().equals("ROLE_USER")) {
                try {
                    cartService.addCartFromSession();
                    cartService.transferFromOrderUpSession();
                    redirectStra.sendRedirect(request, response, "/home");
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else if (a.getAuthority().equals("ROLE_ADMIN")) {
                try {
                    redirectStra.sendRedirect(request, response, "/admin");
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

        });

    }

}

package com.poly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CookieService {
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private HttpServletRequest request;

    public Cookie add(String name, Integer quantity,Integer hours){
        Cookie cookie = new Cookie(name,quantity.toString());
        cookie.setMaxAge(hours*60*60);
        cookie.setPath("/");
        response.addCookie(cookie);
        return cookie;
    }

//    public void update(String name,Integer quantity,Integer hours){
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies ){
//            if(cookie.getName().equals(name)){
//                cookie.setValue(quantity.toString());
//                cookie.setMaxAge(hours*60*60);
//                cookie.setPath("/");
//            }
//        }
//    }

    public void remove(String name){
        add(name,null,0);
    }

    public Cookie get(String name){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie: cookies){
                if(cookie.getName().equals(name)){
                    return cookie;
                }
            }
        }

        return null;
    }

    public String getValue(String name){
        Cookie cookie = get(name);
        return cookie!=null?cookie.getValue():null;
    }
}

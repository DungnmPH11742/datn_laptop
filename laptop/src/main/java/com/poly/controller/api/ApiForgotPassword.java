package com.poly.controller.api;

import com.poly.service.AccountService;
import com.poly.vo.response.ForgotPasswordRespon;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RequestMapping("/api")
@RestController
public class ApiForgotPassword {
    @Autowired
    private AccountService accountService;

    @PostMapping("/forgotPassword/{email}")
    public ForgotPasswordRespon processForgotPassword(HttpServletRequest request, @PathVariable(name = "email") String email) {
        System.out.println(email);
        ForgotPasswordRespon forgotPasswordRespon = new ForgotPasswordRespon();
        String token = RandomString.make(30);


        try {
            accountService.updateResetPasswordToken(token, email);
            String resetPasswordLink = getSiteURL(request) + "/reset_password?token=" + token;
            accountService.sendForgotPasswordEmail(email, resetPasswordLink);
            forgotPasswordRespon.setMessage("Chúng tôi đã gửi một liên kết đặt lại mật khẩu đến email của bạn. Vui lòng kiểm tra.");
        } catch (AccountNotFoundException ex) {
            forgotPasswordRespon.setError(ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            forgotPasswordRespon.setError("Gửi email thất bại. Quý khách vui lòng kiểm tra lại");
        }
        return forgotPasswordRespon;
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}

package com.poly.validate;

import com.poly.service.AccountService;
import com.poly.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AccountValidate implements Validator {
    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountVO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountVO accountVO = (AccountVO) target;
        ValidationUtils.rejectIfEmpty(errors, "fullName", "error.fullName", "Họ tên không được để trống");

        String strPattern = "[~!@#$%^&*()_+{}\\[\\]:;,.<>/?-]";
        Pattern patternPassword = Pattern.compile(strPattern);
        Matcher matcher = patternPassword.matcher(accountVO.getPassword());

        Pattern patternSpace = Pattern.compile("\\s");
        Matcher matcherSpace = patternSpace.matcher(accountVO.getPassword());
        boolean found = matcherSpace.find();
        if (accountVO.getEmail() == null || accountVO.getEmail().equals("")) {
            ValidationUtils.rejectIfEmpty(errors, "email", "error.email", "Email không được để trống");
        } else if (accountVO.getEmail() != null || accountVO.getEmail() != "") {
            Pattern pattern = Pattern.compile("[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?");
            if (!(pattern.matcher(accountVO.getEmail()).matches())) {
                errors.rejectValue("email", "errors.email", "Email sai định dạng vui lòng nhập lại");
            }
        }
        if (accountService.findByEmail(accountVO.getEmail()) != null) {
            errors.rejectValue("email", "errors.email", "Email đã đăng ký vui lòng nhập email khác");
        }

        if (accountVO.getPassword() == null || accountVO.getPassword().equals("")) {
            ValidationUtils.rejectIfEmpty(errors, "password", "error.password", "Password không được để trống");
        } else if (matcher.find()) {
            errors.rejectValue("password", "errors.password", "Mật khẩu không được chứa ký tự đặc biệt");
        } else if (found) {
            errors.rejectValue("password", "errors.password", "Mật khẩu không được chứa khoảng trắng");
        } else if (accountVO.getPassword().length() < 6) {
            errors.rejectValue("password", "errors.password", "Mật khẩu tối thiểu 6 kí tự");
        }

        if (!accountVO.getPassword().equals(accountVO.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "errors.confirmPassword", "Mật khẩu không khớp vui lòng nhập lại");
        }
    }
}

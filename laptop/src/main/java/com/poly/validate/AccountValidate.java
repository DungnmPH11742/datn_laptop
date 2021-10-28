package com.poly.validate;

import com.poly.service.AccountService;
import com.poly.vo.AccountVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class AccountValidate implements Validator {
@Autowired private AccountService service;
    @Override
    public boolean supports(Class<?> clazz) {
        return AccountVO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    AccountVO accountVO = (AccountVO) target;
        ValidationUtils.rejectIfEmpty(errors,"fullName","error.fullName","Họ tên không được để trống");

        if(accountVO.getEmail() == null || accountVO.getEmail().equals("")){
            ValidationUtils.rejectIfEmpty(errors,"email","error.email","Email không được để trống");
        }
       else if(accountVO.getEmail()!=null || accountVO.getEmail()!=""){
            Pattern pattern = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
            if(!(pattern.matcher(accountVO.getEmail()).matches())){
                errors.rejectValue("email","errors.email","Email sai định dạng vui lòng nhập lại");
            }
        }
        if(service.findByEmail(accountVO.getEmail())!=null){
            errors.rejectValue("email","errors.email","Mail đã đăng ký vui lòng nhập mail khác");
        }

        if(accountVO.getPassword()== null || accountVO.getPassword().equals("") ){
            ValidationUtils.rejectIfEmpty(errors,"password","error.password","Password không được để trống");
        }
        else if(accountVO.getPassword().length()<3){
            errors.rejectValue("password","errors.password","Mật khẩu tối thiểu 3 kí tự");
        }
        if(accountVO.getConfirmPassword()== null || accountVO.getConfirmPassword().equals("")){
            ValidationUtils.rejectIfEmpty(errors,"confirmPassword","error.confirmPassword","Xác nhận mật khẩu không được để trống");
        }
       else if(!accountVO.getPassword().equals(accountVO.getConfirmPassword())){
           if(accountVO.getConfirmPassword().length()<3){
                errors.rejectValue("confirmPassword","errors.confirmPassword","Xác nhận mật khẩu tối thiểu 3 kí tự");
            }
           else{
           errors.rejectValue("confirmPassword","errors.confirmPassword","Mật khẩu không khớp vui lòng nhập lại");
       }
       }
    }
}

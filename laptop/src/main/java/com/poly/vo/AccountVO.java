package com.poly.vo;

import com.poly.entity.AuthenticationProvider;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;

@Data
public class AccountVO implements Serializable {

    private Integer id;

    private String fullName;

    private String phone;

    private String email;

    private String password;

    private String imgUrl;

    private Date dateOfBirth;

    private Boolean actived;

    private String nameRoles;

    private String confirmPassword;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeToken;
}

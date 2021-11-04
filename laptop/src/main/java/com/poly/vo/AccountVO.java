package com.poly.vo;

import com.poly.entity.AuthenticationProvider;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class AccountVO implements Serializable {

    private Integer id;

    private String fullName;

    private String phone;

    private String email;

    private String password;

    private Date dateOfBirth;

    private Boolean actived;

    private String nameRoles;

    private AuthenticationProvider authProvider;
}

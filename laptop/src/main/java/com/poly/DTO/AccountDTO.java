package com.poly.DTO;
import com.poly.vo.RoleVO;
import lombok.Data;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class AccountDTO {

    private Integer id;

    private String fullName;

    private Boolean sex;

    private String phone;

    private String email;

    private String password;

    private String imgUrl;

    private Date dateOfBirth;

    private Boolean actived;

    private List<RoleVO> role;

    private Object abc;
//    Comment demo

}

package com.poly.vo;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class ContactVO implements Serializable {

    private Integer id;

    private String name;

    private String email;

    private String subject;

    private String message;

    private String contactDate;

    private String replyDate;

    private String replyContent;

    private String phoneNumber;

    private String status;

    private String contactPersonEmail;

}

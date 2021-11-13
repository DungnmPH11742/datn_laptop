package com.poly.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContactVO implements Serializable {

    private Integer id;

    private String name;

    private String email;

    private String subject;

    private String message;

}

package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class BlogsVO implements Serializable {

    private String title;

    private Integer idAccount;

    private Date dateCreated;

    private String img;

}

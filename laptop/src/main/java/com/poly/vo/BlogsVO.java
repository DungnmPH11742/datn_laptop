package com.poly.vo;

import com.poly.entity.Description;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class BlogsVO implements Serializable {

    private String id;

    private String title;

    private AccountVO account;

    private Date dateCreated;

    private String imgUrl;

    private String descriptionShort;

    private Boolean isHot;

    private List<DescriptionVO> descriptions;

}

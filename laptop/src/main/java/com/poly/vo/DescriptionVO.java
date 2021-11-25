package com.poly.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DescriptionVO implements Serializable {

    private Integer id;

    private String idProduct;

    private String idBlog;

    private String imgUrl;

    private String title;

    private String content;

}

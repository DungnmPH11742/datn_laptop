package com.poly.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DescriptionVO implements Serializable {

    private Integer id;

    private Integer idProduct;

    private String idBlog;

    private String title;

    private String content;

}

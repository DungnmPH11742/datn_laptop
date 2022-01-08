package com.poly.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageDetailVO implements Serializable {

    private Integer id;

    private String sku;

    private String namePath;

    private int typeImg;

}

package com.poly.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageDetailVO implements Serializable {

    private Integer id;

    private Integer idProduct;

    private String namePath;

}

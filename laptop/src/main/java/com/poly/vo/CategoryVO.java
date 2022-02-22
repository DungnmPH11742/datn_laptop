package com.poly.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryVO implements Serializable {

    private String id;

    private String name;

    private Boolean actived;

    private String parentId;

}

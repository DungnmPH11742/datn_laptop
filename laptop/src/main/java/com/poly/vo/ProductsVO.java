package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class ProductsVO implements Serializable {

    private String id;

    private String name;

    private String company;

    private String typeOfItem;

    private String unit;

    private String releaseYear;

    private List<ProductsDetailVO> productsDetails;

    private CategoryVO category;

    private int active;

}

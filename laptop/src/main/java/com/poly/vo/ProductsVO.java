package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class ProductsVO implements Serializable {

    private String id;

    private String name;

    private Date dateOn;

    private Integer typeOfItem;

    private CategoryVO category;

    private Float inputPrice;

    private Float outputPrice;

    private Integer quantity;

    private Float mass;

    private String unit;

    private String releaseDate;

    private String dateOfManufacture;

    private SaleProductVO saleProduct;

    private ProductsDetailVO productsDetail;

    private Boolean active;

}

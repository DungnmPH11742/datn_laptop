package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class ProductsVO implements Serializable {

    private String id;

    private String name;

    private Date dateOn;

    private Integer idCategory;

    private Float inputPrice;

    private Float outputPrice;

    private Integer quantity;

    private Float mass;

    private String unit;

    private Date releaseDate;

    private Date dateOfManufacture;

    private Integer onSale;

    private Boolean active;

}

package com.poly.vo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
    private List<ProductsDetailVO> productsDetails;

    private List<DescriptionVO> descriptions;

    private CategoryVO category;

    private int active;

}

package com.poly.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProductRatingVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private AccountVO account;
    private String comment;
    private String imgUrl;
    private Double starRating;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startComment;

    private ProductsDetailVO productsDetail;
}

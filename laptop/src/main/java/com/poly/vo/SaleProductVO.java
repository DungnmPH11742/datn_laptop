package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class SaleProductVO implements Serializable {

    private Integer id;

    private String promotionType;

    private Date dateOn;

    private Date dateOff;

    private Integer promotion;

    private Integer quantity;

    private Boolean status;

}

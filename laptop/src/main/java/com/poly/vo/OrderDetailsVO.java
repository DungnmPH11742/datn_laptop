package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class OrderDetailsVO implements Serializable {

    private Integer id;

    private Integer idOrder;

    private Integer idProduct;

    private String idVoucher;

    private Float price;

    private Integer quantity;

    private Date completionDate;

    private Integer paymentMethods;

    private Integer status;

}

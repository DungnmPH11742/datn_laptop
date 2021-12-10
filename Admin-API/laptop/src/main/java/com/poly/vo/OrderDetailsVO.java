package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class OrderDetailsVO implements Serializable {

    private Integer id;

    private Integer idOrder;

    private ProductsVO product;

    private VouchersVO voucher;

    private Float price;

    private Integer quantity;

    private Integer status;

}

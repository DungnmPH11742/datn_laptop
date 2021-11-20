package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class OrderDetailsVO implements Serializable {

    private Integer id;

    private OrdersVO ordersVO;

    private ProductsVO productsVO;

    private VouchersVO vouchersVO;

    private Float price;

    private Integer quantity;

    private Date completionDate;

    private Integer received;

}

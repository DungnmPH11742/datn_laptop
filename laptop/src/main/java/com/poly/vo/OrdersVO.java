package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class OrdersVO implements Serializable {

    private Integer id;

    private AccountVO account;

    private Date orderDate;

    private String phoneNumber;

    private String orderCode;

    private String address;

    private String description;

    private Boolean paymentStatus;

    private Integer received;

    private List<OrderDetailsVO> orderDetails;
    private Integer paymentMethods;
    private Float priceOrder;
}

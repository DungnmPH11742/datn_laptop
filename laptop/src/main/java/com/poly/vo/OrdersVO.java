package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class OrdersVO implements Serializable {

    private Integer id;

    private AccountVO accountVO;

    private Date orderDate;

    private String phoneNumber;

    private String address;

    private Integer quantity;

    private String description;

    private Integer paymentMethod;

    private  OrderDetailsVO detailsVO;

}

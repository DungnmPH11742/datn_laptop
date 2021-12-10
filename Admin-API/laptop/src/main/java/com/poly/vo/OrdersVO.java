package com.poly.vo;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class OrdersVO implements Serializable {

    private Integer id;

    private String orderCode;

    private AccountVO account;

    private Date orderDate;

    private String phoneNumber;

    private String address;

    private String description;

    private boolean paymentStatus;

    private Integer paymentMethods;

    private Date completionDate;

    private String authenticator;

    private Integer received;

    private List<OrderDetailsVO> orderDetails;

}

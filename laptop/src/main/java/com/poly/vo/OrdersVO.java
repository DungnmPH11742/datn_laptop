package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class OrdersVO implements Serializable {

    private Integer id;

    private Date orderDate;

    private String phoneNumber;

    private String address;

//    @Column(name = "quantity", nullable = false)
//    private Integer quantity;

    private String description;

    private Integer paymentStatus;

    private Integer received;

    private String orderCode;

    private List<OrderDetailsVO> orderDetailsVO;

    private AccountVO accountVO;

}

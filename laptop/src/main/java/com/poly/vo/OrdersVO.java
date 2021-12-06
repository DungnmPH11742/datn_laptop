package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class OrdersVO implements Serializable {

    private Integer id;

<<<<<<< HEAD
    private AccountVO account;

=======
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
    private Date orderDate;

    private String phoneNumber;

<<<<<<< HEAD
    private String orderCode;

    private String address;

    private String description;

    private Boolean paymentStatus;

    private Integer received;
=======
    private String address;

//    @Column(name = "quantity", nullable = false)
//    private Integer quantity;

    private String description;

    private Integer paymentStatus;

    private Integer received;

    private String orderCode;

    private List<OrderDetailsVO> orderDetailsVO;

    private AccountVO accountVO;
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06

    private List<OrderDetailsVO> orderDetails;
    private Integer paymentMethods;
    private Float priceOrder;
}

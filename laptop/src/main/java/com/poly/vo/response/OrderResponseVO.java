package com.poly.vo.response;

import com.poly.vo.AccountVO;
import com.poly.vo.OrderDetailsVO;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class OrderResponseVO {

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

    private List<OrderDetailResponseVO> orderDetails;

    private Float priceOrder;
}

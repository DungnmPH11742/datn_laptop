package com.poly.vo;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Date;

@Data
public class OrdersVO implements Serializable {

    private Integer id;

    private Integer idAccount;

    private Date orderDate;

    private String phoneNumber;

    private String address;

    private String description;

    private Integer paymentStatus;

    private Integer received;

}

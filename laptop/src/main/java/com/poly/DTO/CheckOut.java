package com.poly.DTO;

import lombok.Data;

@Data
public class CheckOut {

    private Integer idAddress;

    private String description;

    private Integer payment;

    private String voucher;

    private Integer idOrder;

    private String code;
}

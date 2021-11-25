package com.poly.DTO;

import lombok.Data;

@Data
public class CheckOut {

    private String name;

    private String phone;

    private String addressEmail;

    private String city;

    //Huyện
    private String district;

    //Phường
    private String ward;

    private String addressDetail;

    private String description;

    private Integer payment;

    private String voucher;
}

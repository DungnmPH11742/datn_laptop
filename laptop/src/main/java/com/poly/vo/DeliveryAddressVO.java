package com.poly.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeliveryAddressVO implements Serializable {

    private Integer id;

    private String name;

    private String phone;

    private String address;

    private Boolean setAsDefault;

    private Integer idAccount;

}

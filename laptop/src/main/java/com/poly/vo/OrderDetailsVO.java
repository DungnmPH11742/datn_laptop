package com.poly.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailsVO implements Serializable {

    private Integer id;

    private Float productDtPrice;

    private Float intoMoney;

    private Integer quantity;

    private Integer status;

    private String serialNumber;

    private Integer idOrder;

    private ProductsDetailVO productsDetailVO;

    private VouchersVO voucher;




}

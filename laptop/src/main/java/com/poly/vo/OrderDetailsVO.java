package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class OrderDetailsVO implements Serializable {
    private Integer id;

    private Integer idOrder;

    private ProductsVO products;

    private VouchersVO voucher;

    private Float price;

    private Integer quantity;

    private Date completionDate;

<<<<<<< HEAD


    private Integer status;
=======
<<<<<<< HEAD


    private Integer status;
=======
    private Integer received;
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06

    private Integer paymentMethods;
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2

}

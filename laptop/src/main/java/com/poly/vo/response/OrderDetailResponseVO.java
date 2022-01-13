package com.poly.vo.response;

import com.poly.vo.VouchersVO;
import lombok.Data;

@Data
public class OrderDetailResponseVO {

    private Integer id;

    private Float productDtPrice;

    private Float intoMoney;

    private Integer quantity;

    private Integer status;

    private String serialNumber;

    private Integer idOrder;

    private ProductsReponseVO productsReponse;

    private VouchersVO voucher;




}

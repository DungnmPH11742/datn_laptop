package com.poly.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDTO {

    private Integer idOrder;
    //Tổng tiền của giỏ hàng
    private Float totalPriceCart;

    //Tổng tiền giảm của đơn hàng
    private Float totalReducerPrice;

    //Giá trị đơn hàng khi chưa giảm
    private Float totalPriceUnit;

    private List<CartItemDTO> listCartItem = new ArrayList<>();
}

package com.poly.DTO;


import lombok.Data;

@Data
public class CartItemDTO {

    private String idProduct;

    private String nameProduct;

    private String imgUrl;

    //Giá chính của sản phẩm
    private Float priceUnit;

    //Giá của sản phẩm sau khi sale khi sale
    private Float priceSale;

    private Integer quantityProduct;

    private Float totalPriceCartItem;

}

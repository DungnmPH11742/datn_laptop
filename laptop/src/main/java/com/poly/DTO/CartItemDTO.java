package com.poly.DTO;


import lombok.Data;

@Data
public class CartItemDTO {

    private String idProduct;

    private String nameProduct;

    private String imgUrl;

    //Giá chính của sản phẩm
    private Float priceUnit;

    //Giá priceOuput gốc của sản phẩm khi sale
    private Float priceSale;

    //Giá Giảm bằng unitPrice - priceSale
    private Float reducerdPrice;
    private String nameCate;
    private Integer quantityProduct;

    private Float totalPriceCartItem;

    private String idVoucher;

    private Integer idOrderDetail;
}

package com.poly.DTO;


import lombok.Data;

@Data
public class CartItemDTO {

    private String idProduct;

    private String nameProduct;

    private String imgUrl;

    //Giá chính của sản phẩm
    private Float priceUnit;

<<<<<<< HEAD
    //Giá priceOuput gốc của sản phẩm khi sale
    private Float priceSale;

    //Giá Giảm bằng unitPrice - priceSale
    private Float reducerdPrice;
    private String nameCate;
=======
    //Giá của sản phẩm sau khi sale khi sale
    private Float priceSale;

>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
    private Integer quantityProduct;

    private Float totalPriceCartItem;

    private String idVoucher;

    private Integer idOrderDetail;
}

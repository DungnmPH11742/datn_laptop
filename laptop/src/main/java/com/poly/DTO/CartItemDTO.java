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
=======
<<<<<<< HEAD
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2
    //Giá priceOuput gốc của sản phẩm khi sale
    private Float priceSale;

    //Giá Giảm bằng unitPrice - priceSale
    private Float reducerdPrice;
    private String nameCate;
<<<<<<< HEAD
=======
=======
    //Giá của sản phẩm sau khi sale khi sale
    private Float priceSale;

>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2
    private Integer quantityProduct;

    private Float totalPriceCartItem;

    private String idVoucher;

    private Integer idOrderDetail;
}

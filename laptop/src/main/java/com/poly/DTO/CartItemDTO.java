package com.poly.DTO;


import lombok.Data;

@Data
public class CartItemDTO {

    private String sku;

    private String nameProduct;

    private String imgUrl;

    //Giá chính của sản phẩm
    private Float priceUnit;

    //Giá priceOuput gốc của sản phẩm khi sale
    private Float priceSale;

    //Giá Giảm bằng unitPrice - priceSale
    private Float reducerdPrice;

    private String nameCate;
    // Số lượng của đơn hàng
    private Integer quantityProduct;

    // Số lượng sản phẩm có trong Product
    private Integer quantity;

    private Float totalPriceCartItem;

    private String idVoucher;

    private Integer idOrderDetail;
}

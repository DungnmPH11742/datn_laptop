package com.poly.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDetailVO implements Serializable {

    private String sku;
    @Transient
    private String idProduct;
    @Transient
    private String nameProduct;

    private Date dateOn;

    private String imgUrl;

    private Float price;

    private Integer quantity;

    private String dimensions;

    private String cpu;

    private String color;

    private String ram;

    private String hardDrive;

    private String webcam;

    private String vga;

    private String os;

    private String power;

    private String displaySize;

    private String screenRatio;

    private String scanFrequency;

    private String backgroundPanels;

    private String resolution;

    private String contrast;

    private String responseTime;

    private String brightness;

    private String viewScreenshots;

    private List<String> connectivitys;

    private String battery;

    private Integer warrantyPeriod;

    private String accessoriesIncluded;

    private String seeMore;

    private List<ImageDetailVO> imageDetails;

    private SaleProductVO saleProduct;

    private int status;

    private OrderDetailsVO orderDetails;

    @JsonBackReference
    private ProductsVO product;

    @Transient
    private String typeOfItem;


}

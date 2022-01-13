package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class ProductsDetailVO implements Serializable {

    private String sku;

    private String idProduct;

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

    private String connectivity;

    private String battery;

    private Integer warrantyPeriod;

    private String accessoriesIncluded;

    private String seeMore;

    private List<ImageDetailVO> imageDetails;

    private SaleProductVO saleProduct;

    private int status;

    private OrderDetailsVO orderDetails;


}

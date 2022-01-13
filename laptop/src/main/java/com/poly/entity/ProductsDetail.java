package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "products_detail")
public class ProductsDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "sku")
    private String sku;

    @Column(name = "date_on", nullable = false)
    private Date dateOn;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "price")
    private Float price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "cpu")
    private String cpu;

    @Column(name = "color")
    private String color;

    @Column(name = "ram")
    private String ram;

    @Column(name = "hard_drive")
    private String hardDrive;

    @Column(name = "webcam")
    private String webcam;

    @Column(name = "vga")
    private String vga;

    @Column(name = "os")
    private String os;

    @Column(name = "power")
    private String power;

    @Column(name = "display_size")
    private String displaySize;

    @Column(name = "screen_ratio")
    private String screenRatio;

    @Column(name = "scan_frequency")
    private String scanFrequency;

    @Column(name = "background_panels")
    private String backgroundPanels;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "contrast")
    private String contrast;

    @Column(name = "response_time")
    private String responseTime;

    @Column(name = "brightness")
    private String brightness;

    @Column(name = "view_screenshots")
    private String viewScreenshots;

    @Column(name = "connectivity")
    private String connectivity;

    @Column(name = "battery")
    private String battery;

    @Column(name = "warranty_period", nullable = false)
    private Integer warrantyPeriod;

    @Column(name = "accessories_included")
    private String accessoriesIncluded;

    @Column(name = "see_more")
    private String seeMore;

    @Column(name = "status")
    private int status;

    //bi-directional one-to-one association to Product
    @ManyToOne
    @JoinColumn(name="id_product")
    private Products product;

    //bi-directional many-to-one association to ImageDetail
    @OneToMany(mappedBy="productsDetail")
    private List<ImageDetail> imageDetails;

    @ManyToOne
    @JoinColumn(name="on_sale")
    private SaleProduct saleProduct;

    //bi-directional many-to-one association to Description
    @OneToMany(mappedBy="productsDetail")
    private List<Description> descriptions;

    //bi-directional many-to-one association to OrderDetail
    @OneToMany(mappedBy="productsDetail")
    private List<OrderDetails> orderDetails;
}

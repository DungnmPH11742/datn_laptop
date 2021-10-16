package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "products_detail")
public class ProductsDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "producer")
    private String producer;

    @Column(name = "cpu")
    private String cpu;

    @Column(name = "color")
    private String color;

    @Column(name = "ram")
    private String ram;

    @Column(name = "hard_drive")
    private String hardDrive;

    @Column(name = "screen")
    private String screen;

    @Column(name = "webcam")
    private String webcam;

    @Column(name = "vga")
    private String vga;

    @Column(name = "operating_system")
    private String operatingSystem;

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

    @Column(name = "warranty_period", nullable = false)
    private Integer warrantyPeriod;

    @Column(name = "wattage")
    private Float wattage;

    @Column(name = "accessories_included")
    private String accessoriesIncluded;

    @Column(name = "see_more")
    private String seeMore;

    //bi-directional one-to-one association to Product
    @OneToOne
    @JoinColumn(name="id")
    private Products product;
}

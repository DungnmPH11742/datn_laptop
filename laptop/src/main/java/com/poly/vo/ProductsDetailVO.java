package com.poly.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data

public class ProductsDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String producer;

    private String cpu;

    private String color;

    private String ram;

    private String hardDrive;

    private String webcam;

    private String vga;

    private String operatingSystem;

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

    private Integer warrantyPeriod;

    private String accessoriesIncluded;

    private String seeMore;

}

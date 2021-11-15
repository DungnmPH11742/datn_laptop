package com.poly.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Data
public class VouchersVO implements Serializable {

    private String id;

    private Integer idCategory;

    private Integer idProduct;

    private String name;

    private Integer promotion;

    private Date startDay;

    private Date endDate;

    private String description;

    private Integer quantity;

    private Boolean actived;

}

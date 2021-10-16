package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "sale_product")
public class SaleProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "promotion_type", nullable = false)
    private String promotionType;

    @Column(name = "date_on", nullable = false)
    private Date dateOn;

    @Column(name = "date_off", nullable = false)
    private Date dateOff;

    @Column(name = "promotion", nullable = false)
    private Integer promotion;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private Boolean status;

    //bi-directional many-to-one association to Product
    @OneToMany(mappedBy="saleProduct")
    private List<Products> products;
}

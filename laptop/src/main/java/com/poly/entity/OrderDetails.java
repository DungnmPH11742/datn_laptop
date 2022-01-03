package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "order_details")
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_price", nullable = false)
    private Float productPrice;

    @Column(name = "into_money", nullable = false)
    private Float intoMoney;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "status")
    private Integer status;

    @Column(name = "serial_number")
    private String serialNumber;

    //bi-directional many-to-one association to Order
    @ManyToOne
    @JoinColumn(name="id_order")
    private Orders order;

    //bi-directional many-to-one association to Product
    @ManyToOne
    @JoinColumn(name="id_product")
    private Products product;
}

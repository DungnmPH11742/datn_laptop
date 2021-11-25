package com.poly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "completion_date")
    private Date completionDate;

    @Column(name = "received")
    private Integer received;

    @Column(name = "payment_methods")
    private Integer paymentMethod;

    //bi-directional many-to-one association to Order
    @OneToOne
    @JoinColumn(name="id_order")
    private Orders order;

    //bi-directional many-to-one association to Product
    @ManyToOne
    @JoinColumn(name="id_product")
    private Products product;

    //bi-directional many-to-one association to Voucher
    @ManyToOne
    @JoinColumn(name="id_voucher")
    private Vouchers voucher;


}

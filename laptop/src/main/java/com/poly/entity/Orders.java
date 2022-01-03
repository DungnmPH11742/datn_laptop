package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "payment_status")
    private boolean paymentStatus;

    @Column(name = "payment_methods")
    private Integer paymentMethods;

    @Column(name = "completion_date")
    private Date completionDate;

    @Column(name = "authenticator")
    private String authenticator;

    @Column(name = "received")
    private Integer received;

    //bi-directional many-to-one association to OrderDetail
    @OneToMany(mappedBy="order")
    private List<OrderDetails> orderDetails;

    //bi-directional many-to-one association to Account
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_account")
    private Account account;

}

package com.poly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
//    @Column(name = "quantity", nullable = false)
//    private Integer quantity;

>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2
    @Column(name = "description")
    private String description;

    @Column(name = "payment_status")
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2
    private Boolean paymentStatus;

    @Column(name = "payment_methods")
    private Integer paymentMethods;

    @Column(name = "received")
    private Integer received;

    @Column(name = "order_code")
    private String orderCode;

<<<<<<< HEAD
    //bi-directional many-to-one association to OrderDetail
    @OneToMany(mappedBy="order")
    private List<OrderDetails> orderDetails;
=======
    //bi-directional many-to-one association to OrderDetail
    @OneToMany(mappedBy="order")
    private List<OrderDetails> orderDetails;
=======
    private Integer paymentStatus;

    @Column(name = "received")
    private Integer received;
    //bi-directional many-to-one association to OrderDetail
    @OneToMany(mappedBy="order")
    private List<OrderDetails> orderDetails;

    @Column(name = "order_code")
    private String orderCode;
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2

    //bi-directional many-to-one association to Account
    @ManyToOne
    @JoinColumn(name="id_account")
    private Account account;
}

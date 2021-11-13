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

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "description")
    private String description;

    //bi-directional many-to-one association to OrderDetail
    @OneToMany(mappedBy="order")
    private List<OrderDetails> orderDetails;

    //bi-directional many-to-one association to Account
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_account")
    private Account account;

}

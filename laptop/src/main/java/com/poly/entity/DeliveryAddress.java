package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "delivery_address")
public class DeliveryAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "set_as_default")
    private Boolean setAsDefault;

    //bi-directional many-to-one association to Account
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_account")
    private Account account;
}

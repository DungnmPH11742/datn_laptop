package com.poly.entity;

import lombok.Data;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "actived")
    private Boolean actived;

    //bi-directional many-to-one association to Blog
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Blogs> blogs;

//    bi-directional many-to-one association to DeliveryAddress
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<DeliveryAddress> deliveryAddresses;

    //bi-directional many-to-one association to ImportedShipment
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<ImportedShipment> importedShipments;

    //bi-directional many-to-one association to Order
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Orders> orders;

    @ManyToMany
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "id_account"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private List<Role> roles;

    //bi-directional many-to-one association to ProductRating
//    @OneToMany(mappedBy="account")
//    private List<ProductRating> productRatings;


}
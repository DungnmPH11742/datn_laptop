package com.poly.entity;

import lombok.Data;
import org.hibernate.criterion.Order;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
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

    //từ đây, cả đăng kí t nữa nên cứ thêm vào
//    @Column(name = "verification_code", length = 64)
//    private String verificationCode;
//    @Column(name = "time_token")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date timeToken;
//    //bi-directional many-to-one association to Blog
//    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
//    private List<Blogs> blogs;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "auth_provider")
//    private AuthenticationProvider authProvider;
//    hết


    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<DeliveryAddress> deliveryAddresses;

    //bi-directional many-to-one association to ImportedShipment
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<ImportedShipment> importedShipments;

    //bi-directional many-to-one association to Order
    @OneToMany
    private List<Orders> orders;

    @ManyToMany
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "id_account"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private List<Role> roles;


}

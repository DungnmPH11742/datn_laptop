package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "vouchers")
public class Vouchers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "promotion", nullable = false)
    private Integer promotion;

    @Column(name = "start_day", nullable = false)
    private Date startDay;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "actived")
    private Boolean actived;

    //bi-directional many-to-one association to OrderDetail
    @OneToMany(mappedBy="voucher")
    private List<OrderDetails> orderDetails;

    //bi-directional many-to-one association to Category
    @ManyToOne
    @JoinColumn(name="id_category")
    private Category category;

    //bi-directional many-to-one association to Product
    @ManyToOne
    @JoinColumn(name="id_product")
    private Products product;

}

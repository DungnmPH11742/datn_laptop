package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_on", nullable = false)
    private Date dateOn;

    @Column(name = "input_price")
    private Float inputPrice;

    @Column(name = "output_price")
    private Float outputPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "mass")
    private Float mass;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "date_of_manufacture")
    private String dateOfManufacture;

    @Column(name = "active")
    private Boolean active;

    //bi-directional many-to-one association to Description
    @OneToMany(mappedBy="product")
    private List<Description> descriptions;

    //bi-directional many-to-one association to ImageDetail
    @OneToMany(mappedBy="product")
    private List<ImageDetail> imageDetails;

    //bi-directional many-to-one association to OrderDetail
    @OneToMany(mappedBy="product")
    private List<OrderDetails> orderDetails;

    //bi-directional many-to-one association to ProductRating
//    @OneToMany(mappedBy="product")
//    private List<ProductRating> productRatings;

    //bi-directional many-to-one association to Category
    @ManyToOne
    @JoinColumn(name="id_category")
    private Category category;

    //bi-directional many-to-one association to SaleProduct
    @ManyToOne
    @JoinColumn(name="on_sale")
    private SaleProduct saleProduct;

    //bi-directional one-to-one association to ProductsDetail
    @OneToOne(mappedBy="product")
    private ProductsDetail productsDetail;

    //bi-directional many-to-one association to ShipmentDetail
    @OneToMany(mappedBy="product")
    private List<ShipmentDetail> shipmentDetails;

    //bi-directional many-to-one association to Voucher
    @OneToMany(mappedBy="product")
    private List<Vouchers> vouchers;
}

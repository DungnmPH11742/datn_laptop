package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "company")
    private String company;

    @Column(name = "type_of_item", nullable = false)
    private String typeOfItem;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "release_year")
    private String releaseYear;

    @Column(name = "active")
    private int active;

    //bi-directional many-to-one association to ProductRating
//    @OneToMany(mappedBy="product")
//    private List<ProductRating> productRatings;

    //bi-directional many-to-one association to Category
    @ManyToOne
    @JoinColumn(name="id_category")
    private Category category;

    //bi-directional many-to-one association to Category
    @ManyToOne
    @JoinColumn(name="id_acount")
    private Account account;

    //bi-directional one-to-one association to ProductsDetail
    @OneToMany(mappedBy="product")
    private List<ProductsDetail> productsDetails;
}

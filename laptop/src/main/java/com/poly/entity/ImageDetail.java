package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "image_detail")
public class ImageDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_path")
    private String namePath;

    @Column(name = "type_img")
    private int typeImg;

    //bi-directional many-to-one association to Product
    @ManyToOne
    @JoinColumn(name="id_product_dt")
    private ProductsDetail productsDetail;
}

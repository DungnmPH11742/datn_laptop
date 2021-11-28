package com.poly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "image_detail")
public class ImageDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_path", nullable = false)
    private String namePath;

    //bi-directional many-to-one association to Product
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="id_product")
    private Products product;
}

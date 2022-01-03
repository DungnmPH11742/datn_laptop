package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "actived", nullable = false)
    private Boolean actived;

    @Column(name = "parent_id")
    private String parentId;

    //bi-directional many-to-one association to Product
    @OneToMany(mappedBy="category")
    private List<Products> products;

}

package com.poly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "description")
public class Description implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    //bi-directional many-to-one association to Blog
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="id_blog")
    private Blogs blog;

    //bi-directional many-to-one association to Product
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="id_product")
    private Products product;
}

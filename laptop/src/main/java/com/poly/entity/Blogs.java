package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "blogs")
public class Blogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "description_short")
    private String descriptionShort;

    @Column(name = "is_hot")
    private Boolean isHot;

    //bi-directional many-to-one association to Account
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_account")
    private Account account;

    //bi-directional many-to-one association to Description
    @OneToMany(mappedBy="blog")
    private List<Description> descriptions;
}

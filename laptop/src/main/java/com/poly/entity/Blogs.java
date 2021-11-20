package com.poly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(name = "img")
    private String img;

    //bi-directional many-to-one association to Account
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_account")
    private Account account;

    //bi-directional many-to-one association to Description
    @OneToMany(mappedBy="blog")
    private List<Description> descriptions;
}

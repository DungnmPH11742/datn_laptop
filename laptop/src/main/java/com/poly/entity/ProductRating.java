package com.poly.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "product_rating")
public class ProductRating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String comment;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "star_rating")
    private Double starRating;

    @Column(name = "start_comment")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startComment;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Products product;


}

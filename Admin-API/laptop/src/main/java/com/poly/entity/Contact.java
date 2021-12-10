package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "contact_date")
    private String contactDate;

    @Column(name = "reply_date")
    private String replyDate;

    @Column(name = "reply_content")
    private String replyContent;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "contact_person_email")
    private String contactPersonEmail;


}

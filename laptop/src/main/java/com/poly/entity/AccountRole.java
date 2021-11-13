package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "account_role")
@IdClass(AccountRole.class)
public class AccountRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_account", nullable = false)
    private Integer idAccount;

    @Id
    @Column(name = "id_role", nullable = false)
    private Integer idRole;

}

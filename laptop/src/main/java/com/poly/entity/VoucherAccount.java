package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "vouchers_account")
@IdClass(VoucherAccount.class)
public class VoucherAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_account", nullable = false)
    private Integer idAccount;

    @Id
    @Column(name = "id_voucher", nullable = false)
    private String idVoucher;

}

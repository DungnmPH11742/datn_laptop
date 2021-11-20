package com.poly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "imported_shipment")
public class ImportedShipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_added", nullable = false)
    private Date dateAdded;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "status")
    private Boolean status;

    //bi-directional many-to-one association to Account
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_account")
    private Account account;

    //bi-directional many-to-one association to ShipmentDetail
    @OneToMany(mappedBy="importedShipment")
    private List<ShipmentDetail> shipmentDetails;

}

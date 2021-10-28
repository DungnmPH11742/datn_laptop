package com.poly.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "shipment_detail")
@IdClass(ShipmentDetail.class)
public class ShipmentDetail implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Id
    @ManyToOne
    @JoinColumn(name="id_shipment")
    private ImportedShipment importedShipment;


    @Id
    @ManyToOne
    @JoinColumn(name="id_product")
    private Products product;

}

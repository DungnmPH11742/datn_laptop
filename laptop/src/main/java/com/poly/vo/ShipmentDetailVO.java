package com.poly.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShipmentDetailVO implements Serializable {


    private Integer idShipment;

    private Integer idProduct;

    private Float price;

    private Integer quantity;

}

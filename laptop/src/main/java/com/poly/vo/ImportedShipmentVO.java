package com.poly.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class ImportedShipmentVO implements Serializable {

    private Integer id;

    private Date dateAdded;

    private Integer idAccount;

    private String supplier;

    private Boolean status;

}

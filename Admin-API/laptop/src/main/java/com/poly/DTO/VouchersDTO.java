package com.poly.DTO;
import lombok.Data;
import java.sql.Date;

@Data
public class VouchersDTO {
    private String id;

    private Integer idCategory;

    private String nameCategory;

    private String idProduct;

    private String nameProduct;

    private String name;

    private Integer promotion;

    private Date startDay;

    private Date endDate;

    private String description;

    private Integer quantity;

    private Boolean actived;
}

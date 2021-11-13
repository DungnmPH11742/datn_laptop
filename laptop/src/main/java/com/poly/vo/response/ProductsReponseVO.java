package com.poly.vo.response;

import com.poly.vo.SaleProductVO;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class ProductsReponseVO implements Serializable {

    private String id;

    private String name;

    private Date dateOn;

    private Integer typeOfItem;

    private Integer idCategory;

    private Float inputPrice;

    private Float outputPrice;

    private Integer quantity;

    private Float mass;

    private String unit;

    private String releaseDate;

    private String dateOfManufacture;

    private SaleProductVO saleProduct;

    private Boolean active;
}

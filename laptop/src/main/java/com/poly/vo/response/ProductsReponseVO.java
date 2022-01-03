package com.poly.vo.response;

import com.poly.vo.CategoryVO;
import com.poly.vo.DescriptionVO;
import com.poly.vo.ProductsDetailVO;
import com.poly.vo.SaleProductVO;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class ProductsReponseVO implements Serializable {

    private String id;

    private String name;

    private String company;

    private String typeOfItem;

    private String unit;

    private String releaseYear;

    private List<DescriptionVO> descriptions;

    private CategoryVO category;

    private int active;

    private ProductsDetailVO productsDetail;

}

package com.poly.vo.request;

import com.poly.vo.CategoryVO;
import lombok.Data;

@Data
public class ProductRequestVO {
    private String id;

    private String name;

    private String company;

    private String typeOfItem;

    private String unit;

    private String releaseYear;

    private CategoryVO category;

    private int active;
}

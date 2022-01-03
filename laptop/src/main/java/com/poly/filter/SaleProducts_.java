package com.poly.filter;

import com.poly.entity.SaleProduct;

import javax.persistence.metamodel.StaticMetamodel;
import java.sql.Date;

@StaticMetamodel(SaleProduct.class)
public class SaleProducts_ {
    public  static String saleCode;
    public  static Date dateOn;
    public  static Date dateOff;
    public  static Boolean status;
}

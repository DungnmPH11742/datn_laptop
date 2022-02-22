package com.poly.filter;

import com.poly.entity.SaleProduct;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.sql.Date;

@StaticMetamodel(SaleProduct.class)
public class SaleProducts_ {
    public final static String saleCode = "saleCode";
    public final static String dateOn = "dateOn";
    public final static String dateOff = "dateOff";
    public final static String status = "status";
   // public final  static String products =  "products";

}

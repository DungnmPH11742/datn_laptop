package com.poly.filter;

import com.poly.entity.Category;
import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ProductsDetail.class)
public class ProductsDetail_ {

    public final  static String cpu = "cpu";
    public final  static String displaySize = "displaySize";
    public final  static String vga = "vga";
    public final  static String resolution = "resolution";

    public final  static String scanFrequency = "scanFrequency";
    public final  static String screenRatio = "screenRatio";
    public final  static String ram = "ram";

    public final  static String hardDrive = "hardDrive";
    public final  static String mass = "mass";
}

package com.poly.filter;

import com.poly.entity.Products;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Products.class)
public class Products_ {

    //public final  static String saleProduct = "saleProduct";
    public final  static String category = "category";
    public final  static String productsDetails = "productsDetails";

    public final  static String name = "name";
    public final  static String typeOfItem = "typeOfItem";
    //public static volatile SingularAttribute<Products, ProductsDetail_> productsDetails;
}

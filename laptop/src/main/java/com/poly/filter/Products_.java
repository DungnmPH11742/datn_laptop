package com.poly.filter;

import com.poly.entity.Products;

import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Products.class)
public class Products_ {
    public final  static String outputPrice="outputPrice";
    public final  static String saleProduct = "saleProduct";
    public final  static String category = "category";
    public final  static String productsDetail = "productsDetail";
}

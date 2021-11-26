package com.poly.filter;

import com.poly.entity.Category;
import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Products.class)
public class Products_ {
    public final  static String outputPrice="outputPrice";
    public final  static String saleProduct = "saleProduct";
    public final  static String category = "category";
    public final  static String productsDetail = "productsDetail";
}

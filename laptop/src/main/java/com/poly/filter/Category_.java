package com.poly.filter;

import com.poly.entity.Category;

import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Category.class)
public class Category_ {
    public final static  String parentId ="parentId";
    public final  static String products="products";
}

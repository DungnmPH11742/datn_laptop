package com.poly.filter;

import com.poly.entity.Category;
import com.poly.entity.Products;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Products.class)
public class ProductsFilterByCategory {
    public  static  volatile SingularAttribute<Products, Category> category;

}

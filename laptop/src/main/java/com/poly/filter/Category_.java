package com.poly.filter;

import com.poly.entity.Category;
import com.poly.entity.Products;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Category.class)
public class Category_ {
    public final static  String parentId ="parentId";

}

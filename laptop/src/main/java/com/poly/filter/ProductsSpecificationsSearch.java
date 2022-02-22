package com.poly.filter;

import com.poly.entity.Category;
import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;
import com.poly.entity.SaleProduct;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.text.MessageFormat;
import java.util.*;

public final class ProductsSpecificationsSearch {

    public static Specification<ProductsDetail> createProductSpecification(ProductSearchCriteria searchCriteria) {
        return           categoryId(searchCriteria.getCategory())
                        .and(onStatus())
                        .and(productSale(searchCriteria.getStatus()))
                        .and(outputPriceBetween(searchCriteria.getMapPrice()))
                        .and(productSearch(searchCriteria.getName()))
                ;
    }

    public static Specification<ProductsDetail> categoryId(Set<String> categories) {
        return (root, query, builder) -> {
            Join<ProductsDetail, Products> productsJoin = root.join(ProductsDetail_.product);
            query.distinct(true);
            builder.equal(root.get("status"), 1);
            return ObjectUtils.isEmpty(categories) ? builder.conjunction() : productsJoin.get(Products_.typeOfItem).in(categories);
        };
    }

    public static Specification<ProductsDetail> productSale(String sale) {
        return (root, query, builder) -> {
            query.distinct(true);
            if (sale.equals("true")) {
                Join<SaleProduct, ProductsDetail> saleJoin = root.join(ProductsDetail_.saleProduct);
                Path<Date> dateOnPath = saleJoin.get(SaleProducts_.dateOn);
                Path<Date> dateOffPath = saleJoin.get(SaleProducts_.dateOff);
                Predicate startsAfterBegin = builder.lessThanOrEqualTo(dateOnPath, builder.currentDate());
                Predicate endsBeforeEnd = builder.greaterThanOrEqualTo(dateOffPath, builder.currentDate());
                Predicate status = builder.equal(saleJoin.get(SaleProducts_.status), 1);
                Predicate isContained = builder.and(status, startsAfterBegin, endsBeforeEnd);
                return ObjectUtils.isEmpty(sale) ? builder.conjunction() : isContained;
            } else {
                return builder.conjunction();
            }
        };
    }


    public static Specification<ProductsDetail> onStatus() {
        return (root, query, builder) -> {
            query.distinct(true);
            return builder.equal(root.get(ProductsDetail_.status), 1);
        };
    }


    public static Specification<ProductsDetail> productSearch(String search) {
        return (root, query, builder) -> {
            Join<ProductsDetail, Products> productsJoin = root.join(ProductsDetail_.product);
            query.distinct(true);
            Predicate predicateName = builder.or(builder.like(productsJoin.get(Products_.name),
                    contains(search)));
            return ((ObjectUtils.isEmpty(search))) ? builder.conjunction() : builder.and(predicateName);
        };
    }

    public static Specification<ProductsDetail> outputPriceBetween(Map<Float, Float> mapPrice) {
        return (root, query, builder) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = builder.conjunction();
            for (Float key : mapPrice.keySet()) {
                predicate = builder.or(builder.between(root.get(ProductsDetail_.price), key, mapPrice.get(key)));
                predicates.add(predicate);
            }
            Predicate[] p = new Predicate[predicates.size()];
            return (ObjectUtils.isEmpty(mapPrice)) ? builder.conjunction() : builder.or(predicates.toArray(p));
        };
    }

    private static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression);
    }

}
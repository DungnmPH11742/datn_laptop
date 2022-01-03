package com.poly.filter;

import com.poly.entity.Category;
import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class ProductsSpecifications {

    public static Specification<Products> createProductSpecification(ProductSearchCriteria searchCriteria) {
        return
                (categoryId(searchCriteria.getCategory()) != null)
                        ?
                        (categoryIn(searchCriteria.getCategory()))
                                .or(categoryId(searchCriteria.getCategory()))
                                .and(outputPriceBetween(searchCriteria.getMinPrice(), searchCriteria.getMaxPrice()))
                                .and(productCPU(searchCriteria.getCpu()))
                                .and(productRAM(searchCriteria.getRam()))
                                .and(productDisplaySize(searchCriteria.getDisplaySize()))
                                .and(productScanFrequency(searchCriteria.getScanFrequency()))
                                .and(productScreenRatio(searchCriteria.getScreenRatio()))
                                .and(productResolution(searchCriteria.getResolution()))
                                .and(massBetween(searchCriteria.getMinMass(), searchCriteria.getMaxMass()))
                                .and(productVGA(searchCriteria.getVga()))

                        :
                        (outputPriceBetween(searchCriteria.getMinPrice(), searchCriteria.getMaxPrice()))

                                .and(productCPU(searchCriteria.getCpu()))
                                .and(productRAM(searchCriteria.getRam()))
                                .and(productDisplaySize(searchCriteria.getDisplaySize()))
                                .and(productScanFrequency(searchCriteria.getScanFrequency()))
                                .and(productScreenRatio(searchCriteria.getScreenRatio()))
                                .and(productResolution(searchCriteria.getResolution()))
                                .and(massBetween(searchCriteria.getMinMass(), searchCriteria.getMaxMass()))
                                .and(productVGA(searchCriteria.getVga()))
                ;


    }

    public static Specification<Products> categoryId(Set<String> categories) {

        if (CollectionUtils.isEmpty(categories)) {
            return null;
        }

        return (root, query, builder) -> {
            Join<Products, Category> categoryJoin = root.join(Products_.category);
            return categoryJoin.get("id").in(categories);
        };
    }

    public static Specification<Products> categoryIn(Set<String> categories) {

        if (CollectionUtils.isEmpty(categories)) {
            return null;
        }

        return (root, query, builder) -> {
            Join<Products, Category> categoryJoin = root.join(Products_.category);
            return categoryJoin.get(Category_.parentId).in(categories);
        };
    }

    public static Specification<Products> productCPU(String cpu) {

        return (root, query, builder) -> {
            Join<Products, ProductsDetail> producJoin = root.join(Products_.productsDetail, JoinType.LEFT);
            return ObjectUtils.isEmpty(cpu) ? builder.conjunction() : builder.like(producJoin.get(ProductsDetail_.cpu), "%" + cpu + "%");
        };
    }

    public static Specification<Products> productDisplaySize(String displaySize) {

        return (root, query, builder) -> {
            Join<Products, ProductsDetail> producJoin = root.join(Products_.productsDetail, JoinType.LEFT);
            return ObjectUtils.isEmpty(displaySize) ? builder.conjunction() : builder.like(producJoin.get(ProductsDetail_.displaySize), "%" + displaySize + "%");
        };
    }

    public static Specification<Products> productVGA(String vga) {

        return (root, query, builder) -> {
            Join<Products, ProductsDetail> producJoin = root.join(Products_.productsDetail, JoinType.LEFT);
            return ObjectUtils.isEmpty(vga) ? builder.conjunction() : builder.like(producJoin.get(ProductsDetail_.vga), "%" + vga + "%");
        };
    }

    public static Specification<Products> productResolution(String resolution) {
        return (root, query, builder) -> {
            Join<Products, ProductsDetail> producJoin = root.join(Products_.productsDetail, JoinType.LEFT);
            return ObjectUtils.isEmpty(resolution) ? builder.conjunction() : builder.like(producJoin.get(ProductsDetail_.resolution), "%" + resolution + "%");
        };
    }

    public static Specification<Products> productScanFrequency(String scanFrequency) {

        return (root, query, builder) -> {
            Join<Products, ProductsDetail> producJoin = root.join(Products_.productsDetail, JoinType.LEFT);
            return ObjectUtils.isEmpty(scanFrequency) ? builder.conjunction() : builder.like(producJoin.get(ProductsDetail_.scanFrequency), "%" + scanFrequency + "%");
        };
    }

    public static Specification<Products> productScreenRatio(String screenRatio) {

        return (root, query, builder) -> {
            Join<Products, ProductsDetail> producJoin = root.join(Products_.productsDetail, JoinType.LEFT);
            return ObjectUtils.isEmpty(screenRatio) ? builder.conjunction() : builder.like(producJoin.get(ProductsDetail_.screenRatio), "%" + screenRatio + "%");
        };
    }

    public static Specification<Products> productRAM(String ram) {
        return (root, query, builder) -> {
            Join<Products, ProductsDetail> producJoin = root.join(Products_.productsDetail, JoinType.LEFT);
            return ObjectUtils.isEmpty(ram) ? builder.conjunction() : builder.like(producJoin.get("ram"), "%" + ram + "%");
        };
    }

    public static Specification<Products> productHardDrive(String hardDrive) {

        return (root, query, builder) -> {
            Join<Products, ProductsDetail> producJoin = root.join(Products_.productsDetail, JoinType.LEFT);
            return ObjectUtils.isEmpty(hardDrive) ? builder.conjunction() : builder.like(producJoin.get(ProductsDetail_.hardDrive), "%" + hardDrive + "%");
        };
    }

    public static Specification<Products> outputPriceBetween(Optional<Float> minPrice, Optional<Float> maxPrice) {

        return (root, query, builder) -> {
            return minPrice.map(min -> {
                return maxPrice.map(max -> builder.between(root.get(Products_.outputPrice), min, max)
                ).orElse(null);
            }).orElse(null);
        };
    }


    public static Specification<Products> massBetween(Optional<Float> minMass, Optional<Float> maxMass) {

        return (root, query, builder) -> {
            return minMass.map(min -> {
                return maxMass.map(max -> builder.between(root.get(ProductsDetail_.mass), min, max)
                ).orElse(null);
            }).orElse(null);
        };
    }
    public static Specification<Products> dayBetween(Integer id) {
        EntityManager em = null;
        return (root, query, builder) -> {
            Query query1 = em.createNativeQuery("filter_Sales_Products", Products.class);
            query1.setParameter(1, id);
            List<Products> productsList = query1.getResultList();
            return (javax.persistence.criteria.Predicate) productsList;
        };
    }

}
package com.poly.filter;

import com.poly.entity.Category;
import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;
import com.poly.entity.SaleProduct;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.*;
import java.text.MessageFormat;
import java.util.*;

public final class ProductsSpecifications {

    public static Specification<ProductsDetail> createProductSpecification(ProductSearchCriteria searchCriteria) {
        return
                (categoryIn(searchCriteria.getCategory()))
                        .or(categoryId(searchCriteria.getCategory()))
                        .and(onStatus())
                        .and(productSale(searchCriteria.getStatus()))
                        .and(outputPriceBetween(searchCriteria.getMapPrice()))
                        .and(productCPU(searchCriteria.getCpu()))
                        .and(productRAM(searchCriteria.getRam()))
                        //.and(productHardDrive(searchCriteria.getHardDrive()))
                        .and(productDisplaySize(searchCriteria.getDisplaySize()))
                        .and(productScanFrequency(searchCriteria.getScanFrequency()))
                        .and(productScreenRatio(searchCriteria.getScreenRatio()))
                        .and(productResolution(searchCriteria.getResolution()))
                        .and(productVGA(searchCriteria.getVga()))
                        .and(productSearch(searchCriteria.getName()))
                ;
    }

    public static Specification<ProductsDetail> categoryId(Set<String> categories) {
        return (root, query, builder) -> {
            Join<ProductsDetail, Products> productsJoin = root.join(ProductsDetail_.product);
            Join<Category, Products> cateJoin = productsJoin.join(Products_.category);
            query.distinct(true);
            builder.equal(root.get("status"), 1);
            return ObjectUtils.isEmpty(categories) ? builder.conjunction() : cateJoin.get("id").in(categories);
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

    public static Specification<ProductsDetail> categoryIn(Set<String> categories) {
        return (root, query, builder) -> {
            Join<ProductsDetail, Products> productsJoin = root.join(ProductsDetail_.product);
            Join<Category, Products> cateJoin = productsJoin.join(Products_.category);
            query.distinct(true);
            return ObjectUtils.isEmpty(categories) ? builder.conjunction() : cateJoin.get(Category_.parentId).in(categories);
        };
    }

    public static Specification<ProductsDetail> onStatus() {
        return (root, query, builder) -> {
            query.distinct(true);
            return builder.equal(root.get(ProductsDetail_.status), 1);
        };
    }

    public static Specification<ProductsDetail> productCPU(Set<String> cpu) {

        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();
            query.distinct(true);
            Predicate predicate = builder.conjunction();
            for (String c : cpu) {
                predicate = builder.or(builder.like(root.get(ProductsDetail_.cpu),
                        contains(c)));

                predicates.add(predicate);
            }
            Predicate[] p = new Predicate[predicates.size()];

            return ObjectUtils.isEmpty(cpu) ? builder.conjunction() : builder.or(predicates.toArray(p));

            // }
        };
    }

    public static Specification<ProductsDetail> productRAM(Set<String> ram) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.distinct(true);
            Predicate predicate = builder.conjunction();
            for (String c : ram) {
                predicate = builder.or(builder.like(root.get(ProductsDetail_.ram),
                        contains(c)));
                predicates.add(predicate);
            }
            Predicate[] p = new Predicate[predicates.size()];
            return ObjectUtils.isEmpty(ram) ? builder.conjunction() : builder.or(predicates.toArray(p));
        };
    }

    public static Specification<ProductsDetail> productDisplaySize(Set<String> displaySize) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.distinct(true);
            Predicate predicate = builder.conjunction();
            for (String c : displaySize) {
                predicate = builder.or(builder.like(root.get(ProductsDetail_.displaySize),
                        contains(c)));
                predicates.add(predicate);
            }
            Predicate[] p = new Predicate[predicates.size()];

            return ObjectUtils.isEmpty(displaySize) ? builder.conjunction() : builder.or(predicates.toArray(p));

        };

    }

    public static Specification<ProductsDetail> productVGA(Set<String> vga) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.distinct(true);
            Predicate predicate = builder.conjunction();
            for (String c : vga) {
                predicate = builder.or(builder.like(root.get(ProductsDetail_.vga),
                        contains(c)));

                predicates.add(predicate);
            }
            Predicate[] p = new Predicate[predicates.size()];

            return ObjectUtils.isEmpty(vga) ? builder.conjunction() : builder.or(predicates.toArray(p));
        };

    }

    public static Specification<ProductsDetail> productResolution(Set<String> resolution) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.distinct(true);
            Predicate predicate = builder.conjunction();
            for (String c : resolution) {
                predicate = builder.or(builder.like(root.get(ProductsDetail_.resolution),
                        contains(c)));

                predicates.add(predicate);
            }
            Predicate[] p = new Predicate[predicates.size()];

            return ObjectUtils.isEmpty(resolution) ? builder.conjunction() : builder.or(predicates.toArray(p));
        };
    }

    public static Specification<ProductsDetail> productScanFrequency(Set<String> scanFrequency) {
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();
            query.distinct(true);
            Predicate predicate = builder.conjunction();
            for (String c : scanFrequency) {
                predicate = builder.or(builder.like(root.get(ProductsDetail_.scanFrequency),
                        contains(c)));

                predicates.add(predicate);
            }
            Predicate[] p = new Predicate[predicates.size()];

            return ObjectUtils.isEmpty(scanFrequency) ? builder.conjunction() : builder.or(predicates.toArray(p));
        };
    }

    public static Specification<ProductsDetail> productScreenRatio(Set<String> screenRatio) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.distinct(true);
            Predicate predicate = builder.conjunction();
            for (String c : screenRatio) {
                predicate = builder.or(builder.like(root.get(ProductsDetail_.screenRatio),
                        contains(c)));
                predicates.add(predicate);
            }
            Predicate[] p = new Predicate[predicates.size()];
            return ObjectUtils.isEmpty(screenRatio) ? builder.conjunction() : builder.or(predicates.toArray(p));
        };
    }

//    public static Specification<ProductsDetail> productHardDrive(Set<String> hardDrive) {
//        return (root, query, builder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            query.distinct(true);
//            Predicate predicate = builder.conjunction();
//            for (String c : hardDrive) {
//                predicate = builder.or(builder.like(root.get(ProductsDetail_.hardDrive),
//                        contains(c)));
//                predicates.add(predicate);
//            }
//            Predicate[] p = new Predicate[predicates.size()];
//            return ObjectUtils.isEmpty(hardDrive) ? builder.conjunction() : builder.or(predicates.toArray(p));
//        };
//    }

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
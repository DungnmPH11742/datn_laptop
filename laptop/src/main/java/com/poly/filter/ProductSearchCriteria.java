package com.poly.filter;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
public class ProductSearchCriteria {
    private Set<Integer> category;
    private Optional<Float> minPrice;
    private Optional<Float> maxPrice;
    private String cpu;
    private String ram;
    private String hardDrive;
    private String displaySize;
    private String screenRatio;
    private String scanFrequency;
    private String resolution;
    private Optional<Float> minMass;
    private Optional<Float> maxMass;
    private String vga;
    private Integer searchId;
}

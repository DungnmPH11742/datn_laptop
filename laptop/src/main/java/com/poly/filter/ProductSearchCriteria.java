package com.poly.filter;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
public class ProductSearchCriteria {
    private Set<String> category;
    private   Map<Float, Float> mapPrice;
    private   Map<Float, Float> mapMass;
    private Set<String> cpu;
    private Set<String> ram;
    private Set<String> hardDrive;
    private Set<String> displaySize;
    private Set<String> screenRatio;
    private Set<String> scanFrequency;
    private Set<String> resolution;
    private String status;
    private Set<String> vga;
    private Integer searchId;
    private String name;
    private String typeOfItem;


}

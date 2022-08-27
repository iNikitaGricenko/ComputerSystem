package com.wolfhack.cloud.product.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductMiniDTO implements Serializable {
    private final Double id;
    private final String name;
    private final String model;
    private final double cost;
    private final String[] photosNames;
    private final Class className;
}

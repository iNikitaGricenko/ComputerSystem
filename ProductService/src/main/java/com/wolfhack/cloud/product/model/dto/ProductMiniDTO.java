package com.wolfhack.cloud.product.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductMiniDTO implements Serializable {
    private final Long id;
    private final String name;
    private final String model;
    private int quantity;
    private float unitPrice;
    private final String[] photosNames;
    private final String className;
}

package com.wolfhack.cloud.product.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductPOJO implements Serializable {
    private Long id;
    private String name;
    private String model;
    private double cost;
    private String className;
}

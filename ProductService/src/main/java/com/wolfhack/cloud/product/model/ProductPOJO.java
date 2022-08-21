package com.wolfhack.cloud.product.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductPOJO implements Serializable {
    private Double id;
    private String name;
    private String model;
    private double cost;
    private Class className;
}

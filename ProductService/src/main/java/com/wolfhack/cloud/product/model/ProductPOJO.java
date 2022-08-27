package com.wolfhack.cloud.product.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductPOJO implements Serializable {
    private Long id;
    private String name;
    private String model;
    private int quantity;
    private float unitPrice;
    private String className;
}

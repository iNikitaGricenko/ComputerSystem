package com.wolfhack.cloud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductResponseDTO implements Serializable {
    @Schema(example = "3")
    private final String id;
    @Schema(example = "Intel Core i3")
    private final String name;
    @Schema(example = "i83da")
    private final String model;
    @Schema(example = "99.9")
    private final double cost;
    @Schema(example = "com.wolfhack.cloud.dto.ProductRequestDTO")
    private final String className;
}

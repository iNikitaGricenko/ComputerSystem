package com.wolfhack.cloud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ProductRequestDTO implements Serializable {
    @NotNull
    @Schema(example = "Intel Core i3")
    private final String name;
    @NotNull
    @Schema(example = "i83da")
    private final String model;
    @NotNull @Min(0)
    @Schema(example = "99.9")
    private final double cost;
    @NotNull
    @Schema(example = "com.wolfhack.cloud.dto.ProductRequestDTO")
    private final String className;
}

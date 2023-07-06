package com.wolfhack.cloud.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OrderItemRequestDTO implements Serializable {
    @NotNull
    @Schema(example = "3")
    private final Long id;
    @NotNull
    @Schema(example = "Intel Core i3")
    private final String name;
    @NotNull
    @Schema(example = "i83da")
    private final String model;
    @NotNull @Min(1)
    @Schema(example = "99.9")
    private float unitPrice;
    @NotNull @Min(0)
    private int quantity;

    @NotNull
    @Schema(example = "class com.wolfhack.cloud.product.model.Cpu")
    private final String className;
}

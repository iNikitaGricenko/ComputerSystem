package com.wolfhack.cloud.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class OrderRequestDTO implements Serializable {
    @NotNull @Size(min = 5)
    private final String address;
    private final String description;
    @NotNull
    private final CustomerRequestDTO customer;
    @NotNull @Min(1)
    private final int count;
    @NotNull
    private final ProductRequestDTO product;
}

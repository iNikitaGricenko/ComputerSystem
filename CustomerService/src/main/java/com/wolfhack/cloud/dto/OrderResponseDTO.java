package com.wolfhack.cloud.dto;

import com.wolfhack.cloud.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderResponseDTO implements Serializable {
    @Schema(example = "1")
    private final Long id;
    @Schema(example = "st. New-York")
    private final String address;
    @Schema(example = "my descriptio")
    private final String description;
    @Schema(example = "2007-12-03T10:15:30")
    private final LocalDateTime created;
    @Schema(example = "INPROGRESS")
    private final OrderStatus status;
    private final CustomerResponseDTO customer;
    @Schema(example = "false")
    private final boolean isDeleted;
    @Schema(example = "2007-12-03T10:15:30")
    private final LocalDateTime deletedAt;
    @Schema(example = "1")
    private final int count;
    private final ProductResponseDTO product;
}

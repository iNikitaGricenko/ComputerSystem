package com.wolfhack.cloud.customer.model.dto;

import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CustomerOrderResponseDTO implements Serializable {
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
    private final Set<OrderItemResponseDTO> orderItems;
}

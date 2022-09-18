package com.wolfhack.cloud.customer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class AnalyticsResponseDTO implements Serializable {

    @Schema(example = "1999.99")
    private final double totalPrice;
    @Schema(example = "100")
    private final long totalQuantity;

    @Schema(example = "150.0")
    private final double maxPrice;
    @Schema(example = "9.99")
    private final double minPrice;

}

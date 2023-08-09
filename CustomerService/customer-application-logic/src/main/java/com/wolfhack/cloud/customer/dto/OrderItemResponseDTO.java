package com.wolfhack.cloud.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemResponseDTO implements Serializable {
	@Schema(example = "3") private final Long id;
	@Schema(example = "Intel Core i3") private final String name;
	@Schema(example = "i83da") private final String model;
	@Schema(example = "99.9") private float unitPrice;
	@Schema(example = "1") private int quantity;
}

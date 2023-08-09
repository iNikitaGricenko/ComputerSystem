package com.wolfhack.cloud.product.exception;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;

@ApiResponse(responseCode = "404")
@Getter
public class MotherboardNotFoundException extends NotFoundException {

	public MotherboardNotFoundException() {
		super("Motherboard not found");
	}

}

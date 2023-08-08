package com.wolfhack.cloud.product.exception;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;

@ApiResponse(responseCode = "404")
@Getter
public class RamNotFoundException extends NotFoundException {

	public RamNotFoundException() {
		super("Ram not found");
	}

}

package com.wolfhack.cloud.controller;

import com.wolfhack.cloud.customer.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.model.AnalyticsResponse;
import com.wolfhack.cloud.customer.model.AnalyticsSearch;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import com.wolfhack.cloud.handler.error.ErrorBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Order API")
public interface OrderEndpoint {
	@GetMapping
	@PageableAsQueryParam
	Page<CustomerOrderResponseDTO> getAll(Pageable pageable);

	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = @Content(
							schema = @Schema(
									implementation = CustomerOrderResponseDTO.class))),
			@ApiResponse(
					responseCode = "404",
					content = @Content(
							schema = @Schema(
									implementation = ErrorBody.class)))
	})
	CustomerOrderResponseDTO getOne(@PathVariable("id") Long id);

	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					content = @Content(
							schema = @Schema(
									implementation = AnalyticsResponse.class))),
			@ApiResponse(
					responseCode = "404",
					content = @Content(
							schema = @Schema(
									implementation = ErrorBody.class)))
	})
	AnalyticsResponse getAnalytics(@ModelAttribute @Valid AnalyticsSearch analyticsSearchDTO);

	@ApiResponse(
			responseCode = "200",
			content = @Content(
					schema = @Schema(
							implementation = CustomerOrderResponseDTO.class)))
	CustomerOrderResponseDTO add(@Valid @RequestBody CustomerOrderRequestDTO requestDTO);

	@ApiResponse(
			responseCode = "200",
			content = @Content(
					schema = @Schema(
							implementation = CustomerOrderResponseDTO.class)))
	CustomerOrderResponseDTO update(@PathVariable Long id, @RequestParam("status") OrderStatus status);
}

package com.wolfhack.cloud.controller;

import com.wolfhack.cloud.customer.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.factory.IOrderCreator;
import com.wolfhack.cloud.customer.model.AnalyticsResponse;
import com.wolfhack.cloud.customer.model.AnalyticsSearch;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import com.wolfhack.cloud.customer.service.IOrderService;
import com.wolfhack.cloud.handler.error.ErrorBody;
import com.wolfhack.cloud.wrapper.RestPage;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Order API")
@RequiredArgsConstructor
public class OrderRestController {

	private final IOrderCreator orderCreator;
	private final IOrderService orderService;

	@GetMapping
	@PageableAsQueryParam
	public Page<CustomerOrderResponseDTO> getAll(Pageable pageable) {
		return new RestPage<>(orderService.findAll(pageable).map(orderCreator::toResponse));
	}

	@GetMapping("/{id}")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CustomerOrderResponseDTO.class))), @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
	})
	public CustomerOrderResponseDTO getOne(@PathVariable("id") Long id) {
		return orderCreator.toResponse(orderService.findById(id));
	}

	@GetMapping("/analytics")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AnalyticsResponse.class))), @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
	})
	public AnalyticsResponse getAnalytics(@ModelAttribute @Valid AnalyticsSearch analyticsSearchDTO) {
		return orderService.getAnalytics(analyticsSearchDTO);
	}

	@PostMapping
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CustomerOrderResponseDTO.class)))
	public CustomerOrderResponseDTO add(@Valid @RequestBody CustomerOrderRequestDTO requestDTO) {
		CustomerOrder customerOrder = orderCreator.toOrder(requestDTO);
		return orderCreator.toResponse(orderService.save(customerOrder));
	}

	@PutMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CustomerOrderResponseDTO.class)))
	public CustomerOrderResponseDTO update(@PathVariable Long id, @RequestParam("status") OrderStatus status) {
		return orderCreator.toResponse(orderService.changeStatus(id, status));
	}
}

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
@RequiredArgsConstructor
public class OrderRestController implements OrderEndpoint {

	private final IOrderCreator orderCreator;
	private final IOrderService orderService;

	@Override
	@GetMapping
	public Page<CustomerOrderResponseDTO> getAll(Pageable pageable) {
		return new RestPage<>(orderService.findAll(pageable).map(orderCreator::toResponse));
	}

	@Override
	@GetMapping("/{id}")
	public CustomerOrderResponseDTO getOne(@PathVariable("id") Long id) {
		return orderCreator.toResponse(orderService.findById(id));
	}

	@Override
	@GetMapping("/analytics")
	public AnalyticsResponse getAnalytics(@ModelAttribute @Valid AnalyticsSearch analyticsSearchDTO) {
		return orderService.getAnalytics(analyticsSearchDTO);
	}

	@Override
	@PostMapping
	public CustomerOrderResponseDTO add(@Valid @RequestBody CustomerOrderRequestDTO requestDTO) {
		CustomerOrder customerOrder = orderCreator.toOrder(requestDTO);
		return orderCreator.toResponse(orderService.save(customerOrder));
	}

	@Override
	@PutMapping("/{id}")
	public CustomerOrderResponseDTO update(@PathVariable Long id, @RequestParam("status") OrderStatus status) {
		return orderCreator.toResponse(orderService.changeStatus(id, status));
	}
}

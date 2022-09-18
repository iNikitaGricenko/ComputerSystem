package com.wolfhack.cloud.customer.controller;

import com.wolfhack.cloud.customer.model.dto.AnalyticsResponseDTO;
import com.wolfhack.cloud.customer.model.dto.AnalyticsSearchDTO;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.exception.handler.error.ErrorBody;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.service.implement.OrderServiceInterface;
import com.wolfhack.cloud.customer.wrapper.RestPage;
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
    private final OrderServiceInterface orderService;

    @GetMapping
    @PageableAsQueryParam
    public Page<CustomerOrderResponseDTO> getAll(Pageable pageable) {
        return new RestPage<>(orderService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(
                    implementation = CustomerOrderResponseDTO.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(
                    implementation = ErrorBody.class)))})
    public CustomerOrderResponseDTO getOne(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/analytics")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(
                    implementation = AnalyticsResponseDTO.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(
                    implementation = ErrorBody.class)))})
    public AnalyticsResponseDTO getAnalytics(@ModelAttribute @Valid AnalyticsSearchDTO analyticsSearchDTO) {
        return orderService.getAnalytics(analyticsSearchDTO);
    }

    @PostMapping
    @ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = CustomerOrderResponseDTO.class)))
    public CustomerOrderResponseDTO add(@Valid @RequestBody CustomerOrderRequestDTO requestDTO) {
        return orderService.save(requestDTO);
    }
}

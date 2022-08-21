package com.wolfhack.cloud.controller;

import com.wolfhack.cloud.dto.OrderRequestDTO;
import com.wolfhack.cloud.dto.OrderResponseDTO;
import com.wolfhack.cloud.dto.mapper.OrderMapper;
import com.wolfhack.cloud.service.implement.OrderServiceInterface;
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
    private final OrderMapper orderMapper;

    @GetMapping
    @PageableAsQueryParam
    public Page<OrderResponseDTO> getAll(Pageable pageable) {
        return orderService.findAll(pageable).map(orderMapper::toResponseDTO);
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(
                    implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(
                    implementation = OrderResponseDTO.class)))
    })
    public OrderResponseDTO getOne(@PathVariable("id") Long id) {
        return orderMapper.toResponseDTO(orderService.findById(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = OrderResponseDTO.class)))
    public OrderResponseDTO add(@Valid @RequestBody OrderRequestDTO requestDTO) {
        return orderMapper.toResponseDTO(orderService.save(orderMapper.toOrderFromRequest(requestDTO)));
    }
}

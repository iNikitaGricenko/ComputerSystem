package com.wolfhack.cloud.product.controller.rest;

import com.wolfhack.cloud.product.dto.GpuFullDTO;
import com.wolfhack.cloud.product.dto.GpuResponseDTO;
import com.wolfhack.cloud.product.dto.mapper.GpuMapper;
import com.wolfhack.cloud.product.handler.error.ErrorBody;
import com.wolfhack.cloud.product.handler.error.ValidationErrorBody;
import com.wolfhack.cloud.product.service.GpuService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/gpu")
@Tag(name = "Gpu API")
@RequiredArgsConstructor
public class GpuRestController {

    private final GpuService gpuService;
    private final GpuMapper gpuMapper;

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GpuResponseDTO.class)))
    @Parameter(name = "id", example = "1")
    public GpuResponseDTO getGpuById(@PathVariable("id") Double id) {
        return gpuMapper.toGpuResponseDTO(gpuService.findById(id));
    }

    @GetMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public Page<GpuResponseDTO> getGpus(Pageable pageable) {
        return gpuService.findAll(pageable).map(gpuMapper::toGpuResponseDTO);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GpuResponseDTO.class)))
    @ResponseStatus(HttpStatus.CREATED)
    public GpuResponseDTO addGpu(@Valid @RequestBody GpuFullDTO gpu) {
        return  gpuMapper.toGpuResponseDTO(gpuService.save(gpuMapper.toGpu(gpu)));
    }

}

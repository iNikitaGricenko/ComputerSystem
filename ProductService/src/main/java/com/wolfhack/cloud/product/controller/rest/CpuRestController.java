package com.wolfhack.cloud.product.controller.rest;

import com.wolfhack.cloud.product.model.dto.CpuFullDTO;
import com.wolfhack.cloud.product.model.dto.CpuResponseDTO;
import com.wolfhack.cloud.product.mapper.CpuMapper;
import com.wolfhack.cloud.product.exception.handler.error.ErrorBody;
import com.wolfhack.cloud.product.exception.handler.error.ValidationErrorBody;
import com.wolfhack.cloud.product.service.CpuService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/cpu")
@Tag(name = "Cpu API")
@RequiredArgsConstructor
public class CpuRestController {

    private final CpuService cpuService;
    private final CpuMapper cpuMapper;

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CpuResponseDTO.class)))
    @Parameter(name = "id", example = "1")
    public CpuResponseDTO getCpuById(@PathVariable("id") Long id) {
        return cpuMapper.toCpuResponseDTO(cpuService.findById(id));
    }

    @GetMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public Page<CpuResponseDTO> getCpus(Pageable pageable) {
        return cpuService.findAll(pageable).map(cpuMapper::toCpuResponseDTO);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CpuResponseDTO.class)))
    public CpuResponseDTO addCpu(@Valid @RequestBody CpuFullDTO cpu) {
        return cpuMapper.toCpuResponseDTO(cpuService.save(cpuMapper.toCpu(cpu)));
    }

}

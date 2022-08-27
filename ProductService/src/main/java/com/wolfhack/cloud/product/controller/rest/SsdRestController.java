package com.wolfhack.cloud.product.controller.rest;

import com.wolfhack.cloud.product.model.dto.SsdFullDTO;
import com.wolfhack.cloud.product.model.dto.SsdResponseDTO;
import com.wolfhack.cloud.product.mapper.SsdMapper;
import com.wolfhack.cloud.product.exception.handler.error.ErrorBody;
import com.wolfhack.cloud.product.exception.handler.error.ValidationErrorBody;
import com.wolfhack.cloud.product.service.SsdService;
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
@RequestMapping("/api/ssd")
@Tag(name = "Ssd API")
@RequiredArgsConstructor
public class SsdRestController {

    private final SsdService ssdService;
    private final SsdMapper ssdMapper;

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SsdResponseDTO.class)))
    @Parameter(name = "id", example = "1")
    public SsdResponseDTO getSsdById(@PathVariable("id") Long id) {
        return ssdMapper.toSsdResponseDTO(ssdService.findById(id));
    }

    @GetMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public Page<SsdResponseDTO> getSsds(Pageable pageable) {
        return ssdService.findAll(pageable).map(ssdMapper::toSsdResponseDTO);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SsdResponseDTO.class)))
    @ResponseStatus(HttpStatus.CREATED)
    public SsdResponseDTO addSsd(@Valid @RequestBody SsdFullDTO ssd) {
        return ssdMapper.toSsdResponseDTO(ssdService.save(ssdMapper.toSsd(ssd)));
    }
}

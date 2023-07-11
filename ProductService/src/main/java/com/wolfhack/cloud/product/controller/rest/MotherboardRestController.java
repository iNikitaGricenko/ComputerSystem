package com.wolfhack.cloud.product.controller.rest;

import com.wolfhack.cloud.product.exception.handler.error.ErrorBody;
import com.wolfhack.cloud.product.exception.handler.error.ValidationErrorBody;
import com.wolfhack.cloud.product.mapper.MotherboardMapper;
import com.wolfhack.cloud.product.model.dto.MotherboardFullDTO;
import com.wolfhack.cloud.product.model.dto.MotherboardResponseDTO;
import com.wolfhack.cloud.product.service.MotherboardService;
import com.wolfhack.cloud.product.wrapper.RestPage;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/motherboard")
@Tag(name = "Motherboard API")
@RequiredArgsConstructor
public class MotherboardRestController {

    private final MotherboardService motherboardService;
    private final MotherboardMapper motherboardMapper;

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation =
            MotherboardResponseDTO.class)))
    @Parameter(name = "id", example = "1")
    public MotherboardResponseDTO getMotherboardById(@PathVariable("id") Long id) {
        return motherboardMapper.toMotherboardResponseDTO(motherboardService.findById(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public Page<MotherboardResponseDTO> getMotherboards(Pageable pageable) {
        return new RestPage<>(motherboardService.findAll(pageable).map(motherboardMapper::toMotherboardResponseDTO));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "/search")
    @PageableAsQueryParam
    public List<MotherboardResponseDTO> searchMotherboards(Pageable pageable, @RequestParam String searchLine) {
        return motherboardService.searchByTitle(searchLine, pageable).stream().map(motherboardMapper::toMotherboardResponseDTO).toList();
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
    @ResponseStatus(HttpStatus.OK)
    public Long addMotherboard(@Valid @RequestBody MotherboardFullDTO motherboard) {
        return motherboardService.save(motherboardMapper.toMotherboard(motherboard));
    }

}

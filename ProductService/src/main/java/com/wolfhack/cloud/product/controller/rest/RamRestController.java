package com.wolfhack.cloud.product.controller.rest;

import com.wolfhack.cloud.product.exception.handler.error.ErrorBody;
import com.wolfhack.cloud.product.exception.handler.error.ValidationErrorBody;
import com.wolfhack.cloud.product.mapper.RamMapper;
import com.wolfhack.cloud.product.model.dto.RamFullDTO;
import com.wolfhack.cloud.product.model.dto.RamResponseDTO;
import com.wolfhack.cloud.product.service.RamService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/ram")
@Tag(name = "Ram API")
@RequiredArgsConstructor
public class RamRestController {

    private final RamService ramService;
    private final RamMapper ramMapper;

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RamResponseDTO.class)))
    @Parameter(name = "id", example = "1")
    public RamResponseDTO getRamById(@PathVariable("id") Long id) {
        return ramMapper.toRamResponseDTO(ramService.findById(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public Page<RamResponseDTO> getRams(Pageable pageable) {
        return new RestPage<>(ramService.findAll(pageable).map(ramMapper::toRamResponseDTO));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "/search")
    @PageableAsQueryParam
    public List<RamResponseDTO> searchRams(Pageable pageable, @RequestParam String searchLine) {
        return ramService.searchByTitle(searchLine, pageable).stream().map(ramMapper::toRamResponseDTO).toList();
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
    @ResponseStatus(HttpStatus.CREATED)
    public Long addRam(@Valid @RequestBody RamFullDTO ram) {
        return ramService.save(ramMapper.toRam(ram));
    }

    @PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE, consumes = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE})
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
    @Parameter(name = "id", example = "1")
    public String addPhoto(@PathVariable("id") Long id, @RequestParam("photo") MultipartFile photo) throws IOException {
        return ramService.addPhoto(id, photo);
    }

}

package com.wolfhack.cloud.product.controller.rest;

import com.wolfhack.cloud.product.exception.handler.error.ErrorBody;
import com.wolfhack.cloud.product.exception.handler.error.ValidationErrorBody;
import com.wolfhack.cloud.product.mapper.CpuMapper;
import com.wolfhack.cloud.product.model.dto.CpuFullDTO;
import com.wolfhack.cloud.product.model.dto.CpuResponseDTO;
import com.wolfhack.cloud.product.service.CpuService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.*;

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

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@PageableAsQueryParam
	public Page<CpuResponseDTO> getCpus(Pageable pageable) {
		return new RestPage<>(cpuService.findAll(pageable).map(cpuMapper::toCpuResponseDTO));
	}

	@GetMapping(produces = APPLICATION_JSON_VALUE, value = "/search")
	@PageableAsQueryParam
	public List<CpuResponseDTO> searchCpus(Pageable pageable, @RequestParam String searchLine) {
		return cpuService.searchByTitle(searchLine, pageable).stream().map(cpuMapper::toCpuResponseDTO).toList();
	}

	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
	public Long addCpu(@Valid @RequestBody CpuFullDTO cpu) {
		return cpuService.save(cpuMapper.toCpu(cpu));
	}

	@PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE, consumes = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE})
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
	@Parameter(name = "id", example = "1")
	public String addPhoto(@PathVariable("id") Long id, @RequestParam("photo") MultipartFile photo) throws IOException {
		return cpuService.addPhoto(id, photo);
	}

}

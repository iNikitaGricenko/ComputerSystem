package com.wolfhack.cloud.product.controller.search;

import com.wolfhack.cloud.product.model.Gpu;
import com.wolfhack.cloud.product.service.GpuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/gpu")
@Tag(name = "Search")
@RequiredArgsConstructor
public class GpuSearchController {

    private final GpuService gpuService;

    @GetMapping
    @PageableAsQueryParam
    public Page<Gpu> getCpusByQuery(Pageable pageable, @RequestParam("query") String query) {
        return gpuService.searchByQuery(query, pageable);
    }

}

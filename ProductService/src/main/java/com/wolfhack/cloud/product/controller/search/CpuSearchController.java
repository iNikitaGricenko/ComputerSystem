package com.wolfhack.cloud.product.controller.search;

import com.wolfhack.cloud.product.model.Cpu;
import com.wolfhack.cloud.product.service.CpuService;
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
@RequestMapping("/search/cpu")
@Tag(name = "Search")
@RequiredArgsConstructor
public class CpuSearchController {

    private final CpuService cpuService;

    @GetMapping
    @PageableAsQueryParam
    public Page<Cpu> getCpusByQuery(Pageable pageable, @RequestParam("query") String query) {
        return cpuService.searchByQuery(query, pageable);
    }

}

package com.wolfhack.cloud.product.controller.search;

import com.wolfhack.cloud.product.model.Motherboard;
import com.wolfhack.cloud.product.service.MotherboardService;
import com.wolfhack.cloud.product.wrapper.RestPage;
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
@RequestMapping("/search/motherboard")
@Tag(name = "Search")
@RequiredArgsConstructor
public class MotherboardSearchController {

    private final MotherboardService motherboardService;

    @GetMapping
    @PageableAsQueryParam
    public Page<Motherboard> getCpusByQuery(Pageable pageable, @RequestParam("query") String query) {
        return new RestPage<>(motherboardService.searchByQuery(query, pageable));
    }

}

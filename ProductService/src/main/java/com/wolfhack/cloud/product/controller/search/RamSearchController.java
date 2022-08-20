package com.wolfhack.cloud.product.controller.search;

import com.wolfhack.cloud.product.model.Ram;
import com.wolfhack.cloud.product.service.RamService;
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
@RequestMapping("/search/ram")
@Tag(name = "Search")
@RequiredArgsConstructor
public class RamSearchController {

    private final RamService ramService;

    @GetMapping
    @PageableAsQueryParam
    public Page<Ram> getCpusByQuery(Pageable pageable, @RequestParam("query") String query) {
        return ramService.searchByQuery(query, pageable);
    }

}

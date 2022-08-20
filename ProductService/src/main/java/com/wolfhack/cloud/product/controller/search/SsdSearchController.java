package com.wolfhack.cloud.product.controller.search;

import com.wolfhack.cloud.product.model.Ssd;
import com.wolfhack.cloud.product.service.SsdService;
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
@RequestMapping("/search/ssd")
@Tag(name = "Search")
@RequiredArgsConstructor
public class SsdSearchController {

    private final SsdService ssdService;

    @GetMapping
    @PageableAsQueryParam
    public Page<Ssd> getCpusByQuery(Pageable pageable, @RequestParam("query") String query) {
        return ssdService.searchByQuery(query, pageable);
    }

}

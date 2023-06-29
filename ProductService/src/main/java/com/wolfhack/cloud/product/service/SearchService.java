package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.model.dto.ProductMiniDTO;
import com.wolfhack.cloud.product.mapper.ProductMapper;
import com.wolfhack.cloud.product.service.implement.SearchServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService implements SearchServiceInterface {

    private final CpuService cpuService;
    private final GpuService gpuService;
    private final SsdService ssdService;
    private final RamService ramService;
    private final MotherboardService motherboardService;

    private final ProductMapper productMapper;

    @AopLog
    @Override
    public Map<String, Object> search(String query, Pageable pageable) {
        List<ProductMiniDTO> products = new ArrayList<>();
        products.addAll(searchCpu(query, pageable).getContent());
        products.addAll(searchGpu(query, pageable).getContent());
        products.addAll(searchSsd(query, pageable).getContent());
        products.addAll(searchRam(query, pageable).getContent());
        products.addAll(searchMotherboard(query, pageable).getContent());
        return Map.of("pageSize", pageable.getPageSize(),
                "size", products.size(),
                "content", products);
    }

    @AopLog
    private Page<ProductMiniDTO> searchMotherboard(String query, Pageable pageable) {
        return motherboardService.searchByQuery(query, pageable)
                .map(productMapper::toProductDTO);
    }

    @AopLog
    private Page<ProductMiniDTO> searchRam(String query, Pageable pageable) {
        return ramService.searchByQuery(query, pageable)
                .map(productMapper::toProductDTO);
    }

    @AopLog
    private Page<ProductMiniDTO> searchSsd(String query, Pageable pageable) {
        return ssdService.searchByQuery(query, pageable)
                .map(productMapper::toProductDTO);
    }

    @AopLog
    private Page<ProductMiniDTO> searchGpu(String query, Pageable pageable) {
        return gpuService.searchByQuery(query, pageable)
                .map(productMapper::toProductDTO);
    }

    @AopLog
    private Page<ProductMiniDTO> searchCpu(String query, Pageable pageable) {
        return cpuService.searchByQuery(query, pageable)
                .map(productMapper::toProductDTO);
    }

}

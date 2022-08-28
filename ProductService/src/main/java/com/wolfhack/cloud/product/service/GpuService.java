package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.exception.GpuNotFoundException;
import com.wolfhack.cloud.product.model.Gpu;
import com.wolfhack.cloud.product.repository.GpuRepository;
import com.wolfhack.cloud.product.service.implement.GpuServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class GpuService implements GpuServiceInterface {

    private final GpuRepository gpuRepository;

    @Override
    @Cacheable(cacheNames = "gpu")
    public Page<Gpu> findAll(Pageable pageable) {
        return gpuRepository.findAll(pageable);
    }

    @Override
    @CachePut(cacheNames = "gpu", key = "#gpu.id")
    public Gpu save(Gpu gpu) {
        return gpuRepository.save(gpu);
    }

    @Override
    @Cacheable(cacheNames = "gpu", key = "#id")
    public Gpu findById(Long id) {
        return gpuRepository.findById(id)
                .orElseThrow(GpuNotFoundException::new);
    }

    @Override
    public Page<Gpu> searchByQuery(String query, Pageable pageable) {
        query = format("\"%s\"", query);
        return gpuRepository.searchCpusByQuery(query, pageable);
    }
}

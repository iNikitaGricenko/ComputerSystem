package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Gpu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GpuServiceInterface {
    Page<Gpu> findAll(Pageable pageable);

    Gpu save(Gpu gpu);

    Gpu findById(Long id);

    Page<Gpu> searchByQuery(String query, Pageable pageable);
}
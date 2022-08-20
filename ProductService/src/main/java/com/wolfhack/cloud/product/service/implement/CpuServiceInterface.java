package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Cpu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CpuServiceInterface {
    Page<Cpu> findAll(Pageable pageable);

    Cpu save(Cpu cpu);

    Cpu findById(Double id);

    Page<Cpu> searchByQuery(String query, Pageable pageable);
}

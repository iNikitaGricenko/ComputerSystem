package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Ssd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SsdServiceInterface {
    Page<Ssd> findAll(Pageable pageable);

    Ssd save(Ssd ssd);

    Ssd findById(Long id);

    Page<Ssd> searchByQuery(String query, Pageable pageable);
}

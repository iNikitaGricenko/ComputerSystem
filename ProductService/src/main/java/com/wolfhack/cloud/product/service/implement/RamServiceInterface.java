package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Ram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RamServiceInterface {
    Page<Ram> findAll(Pageable pageable);

    Ram save(Ram ram);

    Ram findById(Double id);

    Page<Ram> searchByQuery(String query, Pageable pageable);
}

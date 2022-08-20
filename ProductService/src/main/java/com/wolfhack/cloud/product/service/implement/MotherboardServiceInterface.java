package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Motherboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MotherboardServiceInterface {
    Page<Motherboard> findAll(Pageable pageable);

    Motherboard findById(Double id);

    Motherboard save(Motherboard motherboard);

    Page<Motherboard> searchByQuery(String query, Pageable pageable);
}
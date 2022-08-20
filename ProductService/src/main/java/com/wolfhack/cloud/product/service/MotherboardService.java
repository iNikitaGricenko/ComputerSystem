package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.exception.MotherboardNotFoundException;
import com.wolfhack.cloud.product.model.Motherboard;
import com.wolfhack.cloud.product.repository.MotherboardRepository;
import com.wolfhack.cloud.product.service.implement.MotherboardServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class MotherboardService implements MotherboardServiceInterface {

    private final MotherboardRepository motherboardRepository;

    @Override
    public Page<Motherboard> findAll(Pageable pageable) {
        return motherboardRepository.findAll(pageable);
    }

    @Override
    public Motherboard findById(Double id) {
        return motherboardRepository.findById(id)
                .orElseThrow(MotherboardNotFoundException::new);
    }

    @Override
    public Motherboard save(Motherboard motherboard) {
        return motherboardRepository.save(motherboard);
    }

    @Override
    public Page<Motherboard> searchByQuery(String query, Pageable pageable) {
        query = format("\"%s\"", query);
        return motherboardRepository.searchCpusByQuery(query, pageable);
    }
}

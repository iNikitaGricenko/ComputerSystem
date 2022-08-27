package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.exception.CpuNotFoundException;
import com.wolfhack.cloud.product.model.Cpu;
import com.wolfhack.cloud.product.repository.CpuRepository;
import com.wolfhack.cloud.product.service.implement.CpuServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CpuService implements CpuServiceInterface {

    private final CpuRepository cpuRepository;

    @Override
    public Page<Cpu> findAll(Pageable pageable) {
        return cpuRepository.findAll(pageable);
    }

    @Override
    public Cpu save(Cpu cpu) {
        return cpuRepository.save(cpu);
    }

    @Override
    public Cpu findById(Long id) {
        return cpuRepository.findById(id)
                .orElseThrow(CpuNotFoundException::new);
    }

    @Override
    public Page<Cpu> searchByQuery(String query, Pageable pageable) {
        query = format("\"%s\"", query);
        return cpuRepository.searchCpusByQuery(query, pageable);
    }
}

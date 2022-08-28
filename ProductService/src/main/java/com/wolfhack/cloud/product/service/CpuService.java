package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.exception.CpuNotFoundException;
import com.wolfhack.cloud.product.model.Cpu;
import com.wolfhack.cloud.product.model.DatabaseSequence;
import com.wolfhack.cloud.product.repository.CpuRepository;
import com.wolfhack.cloud.product.service.implement.CpuServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CpuService extends AbstractMongoEventListener<Cpu> implements CpuServiceInterface {

    private final CpuRepository cpuRepository;
    private final DatabaseSequenceService databaseSequenceService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Cpu> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        }
    }

    @Override
    @Cacheable(cacheNames = "cpu_Response_Page")
    public Page<Cpu> findAll(Pageable pageable) {
        return cpuRepository.findAll(pageable);
    }

    @Override
    @CachePut(cacheNames = {"cpu_Response_Page", "cpu"}, key = "#cpu.id")
    public Cpu save(Cpu cpu) {
        cpu.setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        return cpuRepository.save(cpu);
    }

    @Override
    @Cacheable(cacheNames = "cpu", key = "#id")
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

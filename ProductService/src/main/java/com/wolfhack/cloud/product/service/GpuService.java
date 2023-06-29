package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.GpuNotFoundException;
import com.wolfhack.cloud.product.model.DatabaseSequence;
import com.wolfhack.cloud.product.model.Gpu;
import com.wolfhack.cloud.product.repository.GpuRepository;
import com.wolfhack.cloud.product.service.implement.GpuServiceInterface;
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
public class GpuService extends AbstractMongoEventListener<Gpu> implements GpuServiceInterface {

    private final GpuRepository gpuRepository;
    private final DatabaseSequenceService databaseSequenceService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Gpu> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        }
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "gpu_Response_Page")
    public Page<Gpu> findAll(Pageable pageable) {
        return gpuRepository.findAll(pageable);
    }

    @AopLog
    @Override
    @CachePut(cacheNames = {"gpu_Response_Page", "gpu"}, key = "#gpu.id")
    public Gpu save(Gpu gpu) {
        gpu.setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        return gpuRepository.save(gpu);
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "gpu", key = "#id")
    public Gpu findById(Long id) {
        return gpuRepository.findById(id)
                .orElseThrow(GpuNotFoundException::new);
    }

    @AopLog
    @Override
    public Page<Gpu> searchByQuery(String query, Pageable pageable) {
        query = format("\"%s\"", query);
        return gpuRepository.searchCpusByQuery(query, pageable);
    }
}

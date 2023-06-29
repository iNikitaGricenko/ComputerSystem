package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.MotherboardNotFoundException;
import com.wolfhack.cloud.product.model.DatabaseSequence;
import com.wolfhack.cloud.product.model.Motherboard;
import com.wolfhack.cloud.product.repository.MotherboardRepository;
import com.wolfhack.cloud.product.service.implement.MotherboardServiceInterface;
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
public class MotherboardService extends AbstractMongoEventListener<Motherboard> implements MotherboardServiceInterface {

    private final MotherboardRepository motherboardRepository;
    private final DatabaseSequenceService databaseSequenceService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Motherboard> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        }
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "motherboard_Response_Page")
    public Page<Motherboard> findAll(Pageable pageable) {
        return motherboardRepository.findAll(pageable);
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "motherboard", key = "#id")
    public Motherboard findById(Long id) {
        return motherboardRepository.findById(id)
                .orElseThrow(MotherboardNotFoundException::new);
    }

    @AopLog
    @Override
    @CachePut(cacheNames = {"motherboard_Response_Page", "motherboard"}, key = "#motherboard.id")
    public Motherboard save(Motherboard motherboard) {
        motherboard.setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        return motherboardRepository.save(motherboard);
    }

    @AopLog
    @Override
    public Page<Motherboard> searchByQuery(String query, Pageable pageable) {
        query = format("\"%s\"", query);
        return motherboardRepository.searchCpusByQuery(query, pageable);
    }
}

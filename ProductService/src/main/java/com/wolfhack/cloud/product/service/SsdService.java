package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.SsdNotFoundException;
import com.wolfhack.cloud.product.model.DatabaseSequence;
import com.wolfhack.cloud.product.model.Ssd;
import com.wolfhack.cloud.product.repository.SsdRepository;
import com.wolfhack.cloud.product.service.implement.SsdServiceInterface;
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
public class SsdService extends AbstractMongoEventListener<Ssd> implements SsdServiceInterface {

    private final SsdRepository ssdRepository;
    private final DatabaseSequenceService databaseSequenceService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Ssd> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        }
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "ssd_Response_Page")
    public Page<Ssd> findAll(Pageable pageable) {
        return ssdRepository.findAll(pageable);
    }

    @AopLog
    @Override
    @CachePut(cacheNames = {"ssd_Response_Page", "ssd"}, key = "#ssd.id")
    public Ssd save(Ssd ssd) {
        ssd.setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        return ssdRepository.save(ssd);
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "ssd", key = "#id")
    public Ssd findById(Long id) {
        return ssdRepository.findById(id)
                .orElseThrow(SsdNotFoundException::new);
    }

    @AopLog
    @Override
    public Page<Ssd> searchByQuery(String query, Pageable pageable) {
        query = format("\"%s\"", query);
        return ssdRepository.searchCpusByQuery(query, pageable);
    }
}

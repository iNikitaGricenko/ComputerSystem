package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.exception.RamNotFoundException;
import com.wolfhack.cloud.product.model.DatabaseSequence;
import com.wolfhack.cloud.product.model.Ram;
import com.wolfhack.cloud.product.repository.RamRepository;
import com.wolfhack.cloud.product.service.implement.RamServiceInterface;
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
public class RamService extends AbstractMongoEventListener<Ram> implements RamServiceInterface {

    private final RamRepository ramRepository;
    private final DatabaseSequenceService databaseSequenceService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Ram> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        }
    }


    @Override
    @Cacheable(cacheNames = "ram_Response_Page")
    public Page<Ram> findAll(Pageable pageable) {
        return ramRepository.findAll(pageable);
    }

    @Override
    @CachePut(cacheNames = {"ram_Response_Page", "ram"}, key = "#ram.id")
    public Ram save(Ram ram) {
        ram.setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        return ramRepository.save(ram);
    }

    @Override
    @Cacheable(cacheNames = "ram", key = "#id")
    public Ram findById(Long id) {
        return ramRepository.findById(id)
                .orElseThrow(RamNotFoundException::new);
    }

    @Override
    public Page<Ram> searchByQuery(String query, Pageable pageable) {
        query = format("\"%s\"", query);
        return ramRepository.searchCpusByQuery(query, pageable);
    }
}

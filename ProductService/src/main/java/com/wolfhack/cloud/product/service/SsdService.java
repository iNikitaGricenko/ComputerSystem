package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.exception.SsdNotFoundException;
import com.wolfhack.cloud.product.model.Ssd;
import com.wolfhack.cloud.product.repository.SsdRepository;
import com.wolfhack.cloud.product.service.implement.SsdServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class SsdService implements SsdServiceInterface {

    private final SsdRepository ssdRepository;

    @Override
    public Page<Ssd> findAll(Pageable pageable) {
        return ssdRepository.findAll(pageable);
    }

    @Override
    public Ssd save(Ssd ssd) {
        return ssdRepository.save(ssd);
    }

    @Override
    public Ssd findById(Double id) {
        return ssdRepository.findById(id)
                .orElseThrow(SsdNotFoundException::new);
    }

    @Override
    public Page<Ssd> searchByQuery(String query, Pageable pageable) {
        query = format("\"%s\"", query);
        return ssdRepository.searchCpusByQuery(query, pageable);
    }
}

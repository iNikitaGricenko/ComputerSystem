package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.exception.RamNotFoundException;
import com.wolfhack.cloud.product.model.Ram;
import com.wolfhack.cloud.product.repository.RamRepository;
import com.wolfhack.cloud.product.service.implement.RamServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class RamService implements RamServiceInterface {

    private final RamRepository ramRepository;

    @Override
    public Page<Ram> findAll(Pageable pageable) {
        return ramRepository.findAll(pageable);
    }

    @Override
    public Ram save(Ram ram) {
        return ramRepository.save(ram);
    }

    @Override
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

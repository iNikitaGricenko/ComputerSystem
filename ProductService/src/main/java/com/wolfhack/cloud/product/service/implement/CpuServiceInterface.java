package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Cpu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CpuServiceInterface {
    Page<Cpu> findAll(Pageable pageable);

    Long save(Cpu cpu);

    String addPhoto(Long id, MultipartFile multipartFile) throws IOException;

    Cpu findById(Long id);

    Page<Cpu> searchByQuery(String query, Pageable pageable);

    void delete(long id);

    long update(Cpu cpu);
}

package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Gpu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GpuServiceInterface {
    Page<Gpu> findAll(Pageable pageable);

    Long save(Gpu gpu);

    String addPhoto(Long id, MultipartFile multipartFile) throws IOException;

    Gpu findById(Long id);

    Page<Gpu> searchByQuery(String query, Pageable pageable);

    void delete(long id);

    long update(Gpu gpu);
}
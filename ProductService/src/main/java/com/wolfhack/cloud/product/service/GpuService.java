package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.GpuNotFoundException;
import com.wolfhack.cloud.product.model.DatabaseSequence;
import com.wolfhack.cloud.product.model.Gpu;
import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.repository.GpuRepository;
import com.wolfhack.cloud.product.service.implement.GpuServiceInterface;
import com.wolfhack.cloud.product.service.search.GpuSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class GpuService extends AbstractMongoEventListener<Product<Gpu>> implements GpuServiceInterface {

    private final GpuRepository gpuRepository;
    private final GpuSearchService gpuSearchService;
    private final StorageService storageService;
    private final DatabaseSequenceService databaseSequenceService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Product<Gpu>> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        }
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "gpu_Response_Page")
    public Page<Product<Gpu>> findAll(Pageable pageable) {
        return gpuRepository.findAll(pageable);
    }

    @AopLog
    @Override
    @CachePut(cacheNames = {"gpu_Response_Page", "gpu"}, key = "#gpu")
    public Long save(Gpu gpu) {
        Product<Gpu> gpuProduct = new Product<>();
        long id = databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME);

        gpuProduct.setId(id);
        gpuProduct.setItem(gpu);
        Product<Gpu> saved = gpuRepository.save(gpuProduct);
        gpuSearchService.save(saved.getItem(), id);
        return saved.getId();
    }

    @AopLog
    @Override
    public String addPhoto(Long id, MultipartFile multipartFile) throws IOException {
        Product<Gpu> gpu = gpuRepository.findById(id).orElseThrow(GpuNotFoundException::new);
        return storageService.saveFileAndThen(multipartFile, gpu.getItem().getPhotos(), () -> save(gpu.getItem()));
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "gpu", key = "#id")
    public Product<Gpu> findById(Long id) {
        return gpuRepository.findById(id)
            .orElseThrow(GpuNotFoundException::new);
    }

    @AopLog
    @Override
    public List<Gpu> searchByTitle(String query, Pageable pageable) {
        return gpuSearchService.findByTitle(query, pageable);
    }

    @Override
    public void delete(long id) {
        gpuSearchService.delete(id);
        gpuRepository.deleteById(id);
    }

    @Override
    public long update(long id, Gpu gpu) {
        Product<Gpu> byId = gpuRepository.findById(id).orElseThrow(GpuNotFoundException::new);
        byId.setItem(gpu);
        Product<Gpu> saved = gpuRepository.save(byId);
        gpuSearchService.update(saved.getItem(), id);
        return saved.getId();
    }
}

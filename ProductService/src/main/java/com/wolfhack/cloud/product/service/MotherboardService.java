package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.MotherboardNotFoundException;
import com.wolfhack.cloud.product.model.DatabaseSequence;
import com.wolfhack.cloud.product.model.Motherboard;
import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.repository.MotherboardRepository;
import com.wolfhack.cloud.product.service.implement.MotherboardServiceInterface;
import com.wolfhack.cloud.product.service.search.MotherboardSearchService;
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
public class MotherboardService extends AbstractMongoEventListener<Product<Motherboard>> implements MotherboardServiceInterface {

    private final MotherboardRepository motherboardRepository;
    private final MotherboardSearchService motherboardSearchService;
    private final StorageService storageService;
    private final DatabaseSequenceService databaseSequenceService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Product<Motherboard>> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        }
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "motherboard_Response_Page")
    public Page<Product<Motherboard>> findAll(Pageable pageable) {
        return motherboardRepository.findAll(pageable);
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "motherboard", key = "#id")
    public Product<Motherboard> findById(Long id) {
        return motherboardRepository.findById(id)
                .orElseThrow(MotherboardNotFoundException::new);
    }

    @AopLog
    @Override
    @CachePut(cacheNames = {"motherboard_Response_Page", "motherboard"}, key = "#motherboard")
    public Long save(Motherboard motherboard) {
        Product<Motherboard> motherboardProduct = new Product<>();
        long id = databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME);

        motherboardProduct.setId(id);
        motherboardProduct.setItem(motherboard);
        Product<Motherboard> saved = motherboardRepository.save(motherboardProduct);
        motherboardSearchService.save(saved.getItem(), id);
        return saved.getId();
    }

    @AopLog
    @Override
    public String addPhoto(Long id, MultipartFile multipartFile) throws IOException {
        Product<Motherboard> motherboard = motherboardRepository.findById(id)
                .orElseThrow(MotherboardNotFoundException::new);
        return storageService.saveFileAndThen(multipartFile, motherboard.getItem().getPhotos(), () -> save(motherboard.getItem()));
    }

    @AopLog
    @Override
    public List<Motherboard> searchByTitle(String query, Pageable pageable) {
        return motherboardSearchService.findByTitle(query, pageable);
    }

    @Override
    public void delete(long id) {
        motherboardSearchService.delete(id);
        motherboardRepository.deleteById(id);
    }

    @Override
    public long update(long id, Motherboard motherboard) {
        Product<Motherboard> motherboardProduct = motherboardRepository.findById(id).orElseThrow(MotherboardNotFoundException::new);
        motherboardProduct.setItem(motherboard);
        Product<Motherboard> saved = motherboardRepository.save(motherboardProduct);
        motherboardSearchService.update(saved.getItem(), id);
        return saved.getId();
    }
}

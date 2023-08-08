package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.SsdNotFoundException;
import com.wolfhack.cloud.product.model.DatabaseSequence;
import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.model.Ssd;
import com.wolfhack.cloud.product.repository.SsdRepository;
import com.wolfhack.cloud.product.service.implement.SsdServiceInterface;
import com.wolfhack.cloud.product.service.search.SsdSearchService;
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
public class SsdService extends AbstractMongoEventListener<Product<Ssd>> implements SsdServiceInterface {

    private final SsdRepository ssdRepository;
    private final SsdSearchService ssdSearchService;
    private final StorageService storageService;
    private final DatabaseSequenceService databaseSequenceService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Product<Ssd>> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        }
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "ssd_Response_Page")
    public Page<Product<Ssd>> findAll(Pageable pageable) {
        return ssdRepository.findAll(pageable);
    }

    @AopLog
    @Override
    @CachePut(cacheNames = {"ssd_Response_Page", "ssd"}, key = "#ssd")
    public Long save(Ssd ssd) {
        Product<Ssd> ssdProduct = new Product<>();
        long id = databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME);

        ssdProduct.setId(id);
        ssdProduct.setItem(ssd);
        Product<Ssd> saved = ssdRepository.save(ssdProduct);
        ssdSearchService.save(saved.getItem(), id);
        return saved.getId();
    }

    @AopLog
    @Override
    public String addPhoto(Long id, MultipartFile multipartFile) throws IOException {
        Product<Ssd> ssd = ssdRepository.findById(id)
            .orElseThrow(SsdNotFoundException::new);
        return storageService.saveFileAndThen(multipartFile, ssd.getItem().getPhotos(), () -> save(ssd.getItem()));
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "ssd", key = "#id")
    public Product<Ssd> findById(Long id) {
        return ssdRepository.findById(id)
            .orElseThrow(SsdNotFoundException::new);
    }

    @AopLog
    @Override
    public List<Ssd> searchByTitle(String query, Pageable pageable) {
        return ssdSearchService.findByTitle(query, pageable);
    }

    @Override
    public void delete(long id) {
        ssdSearchService.delete(id);
        ssdRepository.deleteById(id);

    }

    @Override
    public long update(long id, Ssd ssd) {
        Product<Ssd> ssdProduct = ssdRepository.findById(id)
            .orElseThrow(SsdNotFoundException::new);
        ssdProduct.setItem(ssd);
        Product<Ssd> saved = ssdRepository.save(ssdProduct);
        ssdSearchService.update(saved.getItem(), id);
        return saved.getId();
    }
}

package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.RamNotFoundException;
import com.wolfhack.cloud.product.model.DatabaseSequence;
import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.model.Ram;
import com.wolfhack.cloud.product.repository.RamRepository;
import com.wolfhack.cloud.product.service.implement.RamServiceInterface;
import com.wolfhack.cloud.product.service.search.RamSearchService;
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
public class RamService extends AbstractMongoEventListener<Product<Ram>> implements RamServiceInterface {

    private final RamRepository ramRepository;
    private final RamSearchService ramSearchService;
    private final StorageService storageService;
    private final DatabaseSequenceService databaseSequenceService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Product<Ram>> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
        }
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "ram_Response_Page")
    public Page<Product<Ram>> findAll(Pageable pageable) {
        return ramRepository.findAll(pageable);
    }

    @AopLog
    @Override
    @CachePut(cacheNames = {"ram_Response_Page", "ram"}, key = "#ram")
    public Long save(Ram ram) {
        Product<Ram> ramProduct = new Product<>();
        long id = databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME);

        ramProduct.setId(id);
        ramProduct.setItem(ram);
        Product<Ram> saved = ramRepository.save(ramProduct);
        ramSearchService.save(saved.getItem(), id);
        return saved.getId();
    }

    @AopLog
    @Override
    public String addPhoto(Long id, MultipartFile multipartFile) throws IOException {
        Product<Ram> ram = ramRepository.findById(id)
                .orElseThrow(RamNotFoundException::new);
        return storageService.saveFileAndThen(multipartFile, ram.getItem().getPhotos(), () -> save(ram.getItem()));
    }

    @AopLog
    @Override
    @Cacheable(cacheNames = "ram", key = "#id")
    public Product<Ram> findById(Long id) {
        return ramRepository.findById(id)
                .orElseThrow(RamNotFoundException::new);
    }

    @AopLog
    @Override
    public List<Ram> searchByTitle(String query, Pageable pageable) {
        return ramSearchService.findByTitle(query, pageable);
    }

    @Override
    public void delete(long id) {
        ramSearchService.delete(id);
        ramRepository.deleteById(id);
    }

    @Override
    public long update(long id, Ram ram) {
        Product<Ram> ramProduct = ramRepository.findById(id).orElseThrow(RamNotFoundException::new);
        ramProduct.setItem(ram);
        Product<Ram> saved = ramRepository.save(ramProduct);
        ramSearchService.update(saved.getItem(), id);
        return saved.getId();
    }
}

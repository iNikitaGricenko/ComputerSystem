package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.SsdNotFoundException;
import com.wolfhack.cloud.product.mapper.SsdMapper;
import com.wolfhack.cloud.product.model.DatabaseSequence;
import com.wolfhack.cloud.product.model.FileStorage;
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

@Service
@RequiredArgsConstructor
public class SsdService extends AbstractMongoEventListener<Product<Ssd>> implements SsdServiceInterface {

	private final SsdRepository ssdRepository;
	private final SsdMapper ssdMapper;
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
	@CachePut(cacheNames = {"ssd_Response_Page", "ssd"}, key = "#ssdProduct.id")
	public Long save(Product<Ssd> ssdProduct) {
		ssdProduct.setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
		Product<Ssd> saved = ssdRepository.save(ssdProduct);
		ssdSearchService.save(saved);
		return saved.getId();
	}

	@AopLog
	@Override
	public String addPhoto(Long id, MultipartFile multipartFile) throws IOException {
		Product<Ssd> ssd = ssdRepository.findById(id).orElseThrow(SsdNotFoundException::new);
		FileStorage fileStorage = storageService.saveFileAndThen(multipartFile, ssd.getPhotos(), () -> update(id, ssd));
		ssd.getPhotos().add(fileStorage);
		return fileStorage.getUrl();
	}

	@AopLog
	@Override
	@Cacheable(cacheNames = "ssd", key = "#id")
	public Product<Ssd> findById(Long id) {
		return ssdRepository.findById(id).orElseThrow(SsdNotFoundException::new);
	}

	@AopLog
	@Override
	public List<Product<Ssd>> searchByTitle(String query, Pageable pageable) {
		return ssdSearchService.findByTitle(query, pageable);
	}

	@Override
	public void delete(long id) {
		ssdSearchService.delete(id);
		ssdRepository.deleteById(id);

	}

	@Override
	public long update(long id, Product<Ssd> ssd) {
		Product<Ssd> ssdProduct = ssdRepository.findById(id).orElseThrow(SsdNotFoundException::new);
		Product<Ssd> saved = ssdRepository.save(ssdMapper.partialUpdate(ssdProduct, ssd));
		ssdSearchService.update(saved, id);
		return saved.getId();
	}
}

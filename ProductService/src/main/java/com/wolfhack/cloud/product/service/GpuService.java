package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.GpuNotFoundException;
import com.wolfhack.cloud.product.mapper.GpuMapper;
import com.wolfhack.cloud.product.model.DatabaseSequence;
import com.wolfhack.cloud.product.model.FileStorage;
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

@Service
@RequiredArgsConstructor
public class GpuService extends AbstractMongoEventListener<Product<Gpu>> implements GpuServiceInterface {

	private final GpuRepository gpuRepository;
	private final GpuMapper gpuMapper;
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
	@CachePut(cacheNames = {"gpu_Response_Page", "gpu"}, key = "#gpuProduct.id")
	public Long save(Product<Gpu> gpuProduct) {
		gpuProduct.setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
		Product<Gpu> saved = gpuRepository.save(gpuProduct);
		gpuSearchService.save(saved);
		return saved.getId();
	}

	@AopLog
	@Override
	public String addPhoto(Long id, MultipartFile multipartFile) throws IOException {
		Product<Gpu> gpu = gpuRepository.findById(id).orElseThrow(GpuNotFoundException::new);
		FileStorage fileStorage = storageService.saveFileAndThen(multipartFile, gpu.getPhotos(), () -> update(id, gpu));
		gpu.getPhotos().add(fileStorage);
		return fileStorage.getUrl();
	}

	@AopLog
	@Override
	@Cacheable(cacheNames = "gpu", key = "#id")
	public Product<Gpu> findById(Long id) {
		return gpuRepository.findById(id).orElseThrow(GpuNotFoundException::new);
	}

	@AopLog
	@Override
	public List<Product<Gpu>> searchByTitle(String query, Pageable pageable) {
		return gpuSearchService.findByTitle(query, pageable);
	}

	@Override
	public void delete(long id) {
		gpuSearchService.delete(id);
		gpuRepository.deleteById(id);
	}

	@Override
	public long update(long id, Product<Gpu> gpu) {
		Product<Gpu> gpuProduct = gpuRepository.findById(id).orElseThrow(GpuNotFoundException::new);
		Product<Gpu> saved = gpuRepository.save(gpuMapper.partialUpdate(gpuProduct, gpu));
		gpuSearchService.update(saved, id);
		return saved.getId();
	}
}

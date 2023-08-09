package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.CpuNotFoundException;
import com.wolfhack.cloud.product.mapper.CpuMapper;
import com.wolfhack.cloud.product.model.Cpu;
import com.wolfhack.cloud.product.model.DatabaseSequence;
import com.wolfhack.cloud.product.model.FileStorage;
import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.repository.CpuRepository;
import com.wolfhack.cloud.product.service.implement.CpuServiceInterface;
import com.wolfhack.cloud.product.service.search.CpuSearchService;
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
public class CpuService extends AbstractMongoEventListener<Product<Cpu>> implements CpuServiceInterface {

	private final CpuRepository cpuRepository;
	private final CpuMapper cpuMapper;
	private final CpuSearchService cpuSearchService;
	private final StorageService storageService;
	private final DatabaseSequenceService databaseSequenceService;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Product<Cpu>> event) {
		if (event.getSource().getId() < 1) {
			event.getSource().setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
		}
	}

	@AopLog
	@Override
	@Cacheable(cacheNames = "cpu_Response_Page")
	public Page<Product<Cpu>> findAll(Pageable pageable) {
		return cpuRepository.findAll(pageable);
	}

	@AopLog
	@Override
	@CachePut(cacheNames = {"cpu_Response_Page", "cpu"}, key = "#cpuProduct.id")
	public Long save(Product<Cpu> cpuProduct) {
		cpuProduct.setId(databaseSequenceService.generateSequence(DatabaseSequence.SEQUENCE_NAME));
		Product<Cpu> saveProduct = cpuRepository.save(cpuProduct);
		cpuSearchService.save(saveProduct);
		return saveProduct.getId();
	}

	@AopLog
	@Override
	public String addPhoto(Long id, MultipartFile multipartFile) throws IOException {
		Product<Cpu> cpu = cpuRepository.findById(id).orElseThrow(CpuNotFoundException::new);
		FileStorage fileStorage = storageService.saveFileAndThen(multipartFile, cpu.getPhotos(), () -> update(id, cpu));
		cpu.getPhotos().add(fileStorage);
		return fileStorage.getUrl();
	}

	@AopLog
	@Override
	@Cacheable(cacheNames = "cpu", key = "#id")
	public Product<Cpu> findById(Long id) {
		return cpuRepository.findById(id).orElseThrow(CpuNotFoundException::new);
	}

	@AopLog
	@Override
	public List<Product<Cpu>> searchByTitle(String query, Pageable pageable) {
		return cpuSearchService.findByTitle(query, pageable);
	}

	@Override
	public void delete(long id) {
		cpuSearchService.delete(id);
		cpuRepository.deleteById(id);
	}

	@Override
	public long update(long id, Product<Cpu> cpu) {
		Product<Cpu> cpuProduct = cpuRepository.findById(id).orElseThrow(CpuNotFoundException::new);
		Product<Cpu> saved = cpuRepository.save(cpuMapper.partialUpdate(cpuProduct, cpu));
		cpuSearchService.update(saved, id);
		return cpuProduct.getId();
	}
}

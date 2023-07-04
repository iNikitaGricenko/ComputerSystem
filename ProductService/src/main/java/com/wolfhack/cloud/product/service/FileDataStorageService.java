package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.exception.NotFoundException;
import com.wolfhack.cloud.product.model.Storage;
import com.wolfhack.cloud.product.repository.FileStorageRepository;
import com.wolfhack.cloud.product.service.implement.FileDataStorageServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class FileDataStorageService implements FileDataStorageServiceInterface {

	private final FileStorageRepository fileStorageRepository;

	@Override
	public String save(Storage storage) {
		return fileStorageRepository.save(storage).getId();
	}

	@Override
	public List<String> save(List<Storage> storage) {
		return fileStorageRepository.saveAll(storage).stream().map(Storage::getId).collect(Collectors.toList());
	}

	@Override
	public void delete(String id) {
		get(id);
		fileStorageRepository.deleteById(id);
	}

	@Override
	public void delete(List<String> ids) {
		getAll(ids);
		fileStorageRepository.deleteAllById(ids);
	}

	@Override
	public Page<Storage> getAll(Pageable pageable) {
		return fileStorageRepository.findAll(pageable);
	}

	@Override
	public List<Storage> getAll(List<String> ids) {
		Iterable<Storage> iterable = fileStorageRepository.findAllById(ids);
		return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public Storage get(String id) {
		return fileStorageRepository.findById(id)
				.orElseThrow(NotFoundException::new);
	}
}

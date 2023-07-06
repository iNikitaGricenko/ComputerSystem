package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.exception.NotFoundException;
import com.wolfhack.cloud.product.model.FileStorage;
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
	public String save(FileStorage fileStorage) {
		return fileStorageRepository.save(fileStorage).getId();
	}

	@Override
	public List<String> save(List<FileStorage> fileStorage) {
		return fileStorageRepository.saveAll(fileStorage).stream().map(FileStorage::getId).collect(Collectors.toList());
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
	public Page<FileStorage> getAll(Pageable pageable) {
		return fileStorageRepository.findAll(pageable);
	}

	@Override
	public List<FileStorage> getAll(List<String> ids) {
		Iterable<FileStorage> iterable = fileStorageRepository.findAllById(ids);
		return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public FileStorage get(String id) {
		return fileStorageRepository.findById(id)
				.orElseThrow(NotFoundException::new);
	}
}

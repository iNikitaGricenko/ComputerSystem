package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.FileStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FileDataStorageServiceInterface {

	String save(FileStorage fileStorage);

	List<String> save(List<FileStorage> fileStorage);

	void delete(String id);

	void delete(List<String> ids);

	Page<FileStorage> getAll(Pageable pageable);

	List<FileStorage> getAll(List<String> ids);

	FileStorage get(String id);

}
